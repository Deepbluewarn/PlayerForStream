package club.rodong.playerforstream.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import club.rodong.playerforstream.POJO.LiveDetail;
import club.rodong.playerforstream.POJO.Twitch_v5_Get_Stream_By_User;
import club.rodong.playerforstream.interfaces.ItemClickListener;
import club.rodong.playerforstream.R;
import club.rodong.playerforstream.RetrofitHelper;
import club.rodong.playerforstream.SharedPreferenceHelper;
import club.rodong.playerforstream.interfaces.Twitch_API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RV_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemClickListener {
    private ArrayList<LiveDetail> itemList = new ArrayList<>();
    private Context context;
    public static final int TYPE_ITEM = 1;//방송 미리보기
    public static final int TYPE_HEADER = 2;//방송 없음 표시

    private SharedPreferenceHelper SPH;
    private final RequestManager glide;

    public RV_Adapter(RequestManager GlideApp, Context context){
        glide = GlideApp;
        SPH = new SharedPreferenceHelper(context);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_ITEM){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            view = layoutInflater.inflate(R.layout.onair_recyclerview, parent, false);
            return new RV_Adapter.LiveViewHolder(view, this);
        }else{
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            view = layoutInflater.inflate(R.layout.no_onair_recyclerview, parent, false);
            return new RV_Adapter.NoLiveviewholder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = holder.getItemViewType();
        if(type == TYPE_ITEM){
            LiveViewHolder liveViewHolder = (LiveViewHolder)holder;
            if(position != RecyclerView.NO_POSITION){
                if (itemList.get(position).getThumbnail_url() != null) {
                    //라이브 방송 썸네일 받아옴
                    glide
                            .load(itemList.get(position).getThumbnail_url())
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            .apply(RequestOptions.skipMemoryCacheOf(true))
                            .thumbnail(0.5f)
                            .into(liveViewHolder.thumbIv);
                }
                if (itemList.get(position).getIcon_url() != null) {
                    //프로필 아이콘 받아옴
                    glide
                            .load(itemList.get(position).getIcon_url())
                            /*.apply(RequestOptions.circleCropTransform())*/
                            .into(liveViewHolder.iconIv);
                }
                liveViewHolder.IDtv.setText(Long.toString(itemList.get(position).getId()));
                liveViewHolder.nameTv.setText(itemList.get(position).getDisplay_name());
                liveViewHolder.loginTv.setText(itemList.get(position).getName());
                liveViewHolder.titleTv.setText(itemList.get(position).getTitle());
                liveViewHolder.viewcountTv.setText(Long.toString(itemList.get(position).getViewcount()) + " 명 시청 중");
            }
        }else{
            NoLiveviewholder noLiveviewholder = (NoLiveviewholder)holder;
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public ArrayList<LiveDetail> getItemList() {
        return itemList;
    }
    @Override
    public int getItemViewType(int position){
        if(itemList.get(position).getViewType() == TYPE_ITEM){
            return TYPE_ITEM;
        }else{
            return TYPE_HEADER;
        }
    }

    //클릭 to Refresh
    @Override
    public void onItemClick(final View v, int p) {
        final int position = p;
        TextView IDtv = v.findViewById(R.id.ID);
        final int ID = Integer.parseInt(IDtv.getText().toString());//리스트뷰 아이템에 붙여놓은 PDID 값 가져옴.

        final Twitch_API twitch_API = RetrofitHelper.getRetrofit_Json(context.getString(R.string.twitch_BaseUrl)).create(Twitch_API.class);
        Call<Twitch_v5_Get_Stream_By_User> getStreamByUserCall = twitch_API.Twitch_v5_Get_Stream_By_User("application/vnd.twitchtv.v5+json","5s8icgxpodxmo6oajt6nk20x2q6yrc",ID);
        getStreamByUserCall.enqueue(new Callback<Twitch_v5_Get_Stream_By_User>() {
            @Override
            public void onResponse(Call<Twitch_v5_Get_Stream_By_User> call, Response<Twitch_v5_Get_Stream_By_User> response) {
                Twitch_v5_Get_Stream_By_User twitch = response.body();
                if(twitch != null && twitch.getStream() != null){
                    Twitch_v5_Get_Stream_By_User.Channel channel = twitch.getStream().getChannel();
                    int width = SPH.get_t_width();
                    int height = SPH.get_t_height();
                    String title = channel.getStatus();
                    String iconUrl = channel.getLogo();
                    String name = channel.getName();
                    String display_name = channel.getDisplayName();
                    String thumbUri = twitch.getStream().getPreview().getTemplate()
                            .replace("{width}",String.valueOf(width))
                            .replace("{height}",String.valueOf(height));
                    Long id = channel.getId();
                    int viewer = twitch.getStream().getViewers();


                    refresh_Item(context.getString(R.string.TWITCH), position, name, display_name, thumbUri, iconUrl, title, viewer, 0, id);
                }else if(response.code() == 429){
                    Toast.makeText(context, context.getString(R.string.TooManyReq), Toast.LENGTH_SHORT).show();
                }else{
                    remove(position);
                    Toast.makeText(context, context.getResources().getString(R.string.E_404), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Twitch_v5_Get_Stream_By_User> call, Throwable t) {
                Log.i("RV_Adapter", "onFailure: Twitch_v5_Get_Stream_By_User 실패 " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onItemLongClick(View v, int position) {
        TextView Login = v.findViewById(R.id.login);//채널 스트리머 UserID
        TextView IDtv = v.findViewById(R.id.ID);//채널 ID
        String Channel = Login.getText().toString();
        String ID = IDtv.getText().toString();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("simpletwitch://stream_player?channel=" + Channel + "&id=" + ID));

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    class LiveViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbIv;//방송 썸네일
        private ImageView iconIv;//프로필 이미지
        private TextView titleTv;
        private TextView nameTv;
        private TextView loginTv;
        private TextView viewcountTv;
        private TextView IDtv;
        private CardView cardView;

        public LiveViewHolder(View itemView, final ItemClickListener itemClickListener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        itemClickListener.onItemClick(v,getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        itemClickListener.onItemLongClick(v,getAdapterPosition());
                    }
                    return true;
                }
            });
            thumbIv = itemView.findViewById(R.id.thumb);
            iconIv = itemView.findViewById(R.id.UserIcon);
            titleTv = itemView.findViewById(R.id.Titletv);
            nameTv = itemView.findViewById(R.id.disp_name);
            loginTv = itemView.findViewById(R.id.login);
            viewcountTv = itemView.findViewById(R.id.Viewtv);
            IDtv = itemView.findViewById(R.id.ID);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
    static class NoLiveviewholder extends RecyclerView.ViewHolder{
        public NoLiveviewholder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public void add_no_live(){
        LiveDetail item = new LiveDetail();
        item.setViewType(TYPE_HEADER);
        itemList.add(item);
        notifyDataSetChanged();
    }
    public void addItem(String platform, String name, String username, String thumb, String iconaddr, String title,long viewer,int clap,long id) {
        Log.i("addItem", "addItem: 실행");
        LiveDetail item = new LiveDetail();
        item.setViewType(TYPE_ITEM);
        item.setName(name);
        item.setDisplay_name(username);
        item.setThumbnail_url(thumb);
        item.setIcon_url(iconaddr);
        item.setTitle(title);
        item.setPlatform(platform);
        item.setViewcount(viewer);
        item.setClap(clap);
        item.setId(id);

        itemList.add(item);
    }
    public void refresh_Item(String platform, int position, String name, String username, String thumb, String iconaddr, String title, int viewer, int clap, Long id){
        LiveDetail item = new LiveDetail();
        item.setViewType(TYPE_ITEM);
        item.setName(name);
        item.setDisplay_name(username);
        item.setThumbnail_url(thumb);
        item.setIcon_url(iconaddr);
        item.setTitle(title);
        item.setPlatform(platform);
        item.setViewcount(viewer);
        item.setClap(clap);
        item.setId(id);
        itemList.set(position,item);
        notifyItemChanged(position);
    }
    public void clear(){
        itemList.clear();
        notifyDataSetChanged();
    }
    public void remove(int position){
        try{
            itemList.remove(position);
            notifyItemRemoved(position);
        }catch(IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }
}
