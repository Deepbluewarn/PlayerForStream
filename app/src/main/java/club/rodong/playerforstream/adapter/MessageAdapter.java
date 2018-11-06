package club.rodong.playerforstream.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import club.rodong.playerforstream.SharedPreferenceHelper;
import club.rodong.playerforstream.activity.Stream_Player_Activity;
import club.rodong.playerforstream.interfaces.ItemClickListener;
import club.rodong.playerforstream.POJO.MessageObj;
import club.rodong.playerforstream.R;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemClickListener {
    private static final int LeftViewType = 0;
    private static final int RightViewType = 1;
    private static final int InfoViewType = 2;
    private static final int BitViewType = 3;

    private static final int LeftBadgeViewType = 4;
    private static final int RightBadgeViewType = 5;
    private static final int SubScribeViewType = 6;
    private ArrayList<MessageObj> Items = new ArrayList<>();
    private RecyclerView rv;
    private boolean lock = false;
    private Context context;
    private SharedPreferenceHelper SPH;
    public MessageAdapter(Context context, RecyclerView recyclerView, SharedPreferenceHelper SPH) {
        this.rv = recyclerView;
        this.context = context;
        this.SPH = SPH;

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!rv.canScrollVertically(1)) {
                    lock = false;
                } else if (!rv.canScrollVertically(-1)) {
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if(dy > 0){
                }else if(dy < 0){
                    lock = true;
                }
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case LeftViewType:
                return new MessageAdapter.LeftViewHolder(
                        layoutInflater.inflate(R.layout.message_left, parent, false),this);
            case RightViewType:
                return new MessageAdapter.RightViewHolder(
                        layoutInflater.inflate(R.layout.message_right, parent, false),this);
            case InfoViewType:
                return new MessageAdapter.LeftViewHolder(
                        layoutInflater.inflate(R.layout.message_left, parent, false),this);
            case BitViewType:
                return new MessageAdapter.LeftViewHolder(
                        layoutInflater.inflate(R.layout.message_left, parent, false),this);
            case LeftBadgeViewType:
                return new MessageAdapter.LeftViewHolder(
                        layoutInflater.inflate(R.layout.message_left, parent, false),this);
            case RightBadgeViewType:
                return new MessageAdapter.RightViewHolder(
                        layoutInflater.inflate(R.layout.message_right, parent, false),this);
            case SubScribeViewType:
                return new MessageAdapter.SubscribeViewHolder(
                        layoutInflater.inflate(R.layout.message_subscribe,parent,false));
        }
        return new MessageAdapter.LeftViewHolder(
                layoutInflater.inflate(R.layout.message_left, parent, false),this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final String name;
        final String message;
        final String color;
        final String badge;
        String mod;
        final String emotes;
        final String bits_str;
        String commandType;
        String user_id;

        LinkedHashMap<Bitmap, String> emote_bitmaps;
        ArrayList<Bitmap> badge_bitmaps;

        if(Items.get(position).getDisplay_name() == null){
            name = "";
        }else{
            name = Items.get(position).getDisplay_name();
        }
        message = Items.get(position).getMessage();
        color = Items.get(position).getColor();
        badge = Items.get(position).getBadges();
        mod = Items.get(position).getMod();
        bits_str = Items.get(position).getBits();
        commandType = Items.get(position).getCommandType();
        user_id = Items.get(position).getUser_id();
        emote_bitmaps = Items.get(position).getEmote_bitmaps();
        badge_bitmaps = Items.get(position).getBadge_bitmaps();

        if(holder.getItemViewType() == LeftViewType){
            final LeftViewHolder leftViewHolder = (LeftViewHolder)holder;
            final SpannableStringBuilder Name_Builder = new SpannableStringBuilder();
            final SpannableStringBuilder Message_Builder = new SpannableStringBuilder();
            Name_Builder.append(name);
            //닉네임 컬러, style 적용
            Name_Builder.setSpan(new ForegroundColorSpan(Color.parseColor(color)), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            Name_Builder.setSpan(new StyleSpan(Typeface.BOLD),0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            Name_Builder.append(" : ");
            Message_Builder.append(message);

            if(commandType.equals(context.getString(R.string.COMMAND_ACTION))) {
                Message_Builder.setSpan(new ForegroundColorSpan(Color.parseColor(color)), 0, message.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            leftViewHolder.Message.setText(String.format("%s%s", Name_Builder.toString(), Message_Builder.toString()));

            if(emote_bitmaps != null){
                for (Map.Entry<Bitmap,String> entry : emote_bitmaps.entrySet()) {
                    Bitmap emote = entry.getKey();
                    String index = entry.getValue();

                    String[] index_s = index.split(",");
                    for(int j = 0; j < index_s.length; j++){
                        String[] index_l = index_s[j].split("-");
                        //Bitmap resized = Bitmap.createScaledBitmap(emote, textHeight + 20, textHeight + 20, true);
                        Message_Builder.setSpan(new ImageSpan(context, emote, ImageSpan.ALIGN_BOTTOM), Integer.valueOf(index_l[0]), Integer.valueOf(index_l[1]) + 1,0);
                    }
                }
            }
            Name_Builder.append(Message_Builder);
            leftViewHolder.Message.setText(Name_Builder);

        }else if(holder.getItemViewType() == LeftBadgeViewType){
            //Main Chat. or Admin, Moderator..
            final LeftViewHolder leftViewHolder = (LeftViewHolder)holder;
            final int textHeight = leftViewHolder.Message.getLineHeight();

            final SpannableStringBuilder Message_Builder = new SpannableStringBuilder();
            final SpannableStringBuilder Name_Builder = new SpannableStringBuilder();
            final SpannableStringBuilder Badge_Builder = new SpannableStringBuilder();

            if(badge != null && !badge.equals("")){
                Name_Builder.append(name);
                Name_Builder.setSpan(new ForegroundColorSpan(Color.parseColor(color)), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                Name_Builder.setSpan(new StyleSpan(Typeface.BOLD),0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                Name_Builder.append(" : ");

                Message_Builder.append(message);

                if(commandType.equals(context.getString(R.string.COMMAND_ACTION))){
                    Message_Builder.setSpan(new ForegroundColorSpan(Color.parseColor(color)), 0, Message_Builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                leftViewHolder.Message.setText(String.format("%s%s", Name_Builder.toString(), Message_Builder.toString()));

                if(emote_bitmaps != null){
                    for (Map.Entry<Bitmap,String> entry : emote_bitmaps.entrySet()) {
                        Bitmap emote = entry.getKey();
                        String index = entry.getValue();

                        String[] index_s = index.split(",");
                        for(int j = 0; j < index_s.length; j++){
                            String[] index_l = index_s[j].split("-");
                            //Bitmap resized = Bitmap.createScaledBitmap(emote, textHeight + 20, textHeight + 20, true);
                            Message_Builder.setSpan(new ImageSpan(context, emote, ImageSpan.ALIGN_BASELINE), Integer.valueOf(index_l[0]), Integer.valueOf(index_l[1]) + 1,0);
                            //Message_Builder.setSpan(new VerticalImageSpan(new BitmapDrawable(context.getResources(), resized)),Integer.valueOf(index_l[0]), Integer.valueOf(index_l[1]) + 1,0);
                        }
                    }
                }
                if(badge_bitmaps != null && badge_bitmaps.size() != 0){
                    int[] a = {0,2,4,6,8,10};
                    for(int i = 0; i < badge_bitmaps.size(); i++){
                        Badge_Builder.append("  ");//빈 문자열 2개 넣고
                        Bitmap resized = Bitmap.createScaledBitmap(badge_bitmaps.get(i), textHeight + 10, textHeight + 10, true);
                        Badge_Builder.setSpan(new ImageSpan(context, resized, ImageSpan.ALIGN_BOTTOM), a[i], a[i] + 1,0);
                    }
                    Badge_Builder.append(Name_Builder.append(Message_Builder));
                    leftViewHolder.Message.setText(Badge_Builder);
                }
            }
        }else if(holder.getItemViewType() == RightBadgeViewType){
            Log.i("onBindViewHolder", "RightBadgeViewType 실행");
            final RightViewHolder rightViewHolder = (RightViewHolder)holder;
            final int textHeight = rightViewHolder.Message.getLineHeight();
            final SpannableStringBuilder Name_builder = new SpannableStringBuilder();
            final SpannableStringBuilder Badge_builder = new SpannableStringBuilder();
            final SpannableStringBuilder Message_Builder = new SpannableStringBuilder();
            Message_Builder.append(message);

            if(commandType.equals(context.getString(R.string.COMMAND_ACTION))){
                Message_Builder.setSpan(new ForegroundColorSpan(Color.parseColor(color)), 0, message.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            Name_builder.append(name);//닉네임
            Name_builder.setSpan(new ForegroundColorSpan(Color.parseColor(color)), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            Name_builder.setSpan(new StyleSpan(Typeface.BOLD),0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            rightViewHolder.Name.setText(Name_builder);
            rightViewHolder.Message.setText(Message_Builder);

            if(emote_bitmaps != null){
                for (Map.Entry<Bitmap,String> entry : emote_bitmaps.entrySet()) {
                    Bitmap emote = entry.getKey();
                    String index = entry.getValue();

                    String[] index_s = index.split(",");
                    for(int j = 0; j < index_s.length; j++){
                        String[] index_l = index_s[j].split("-");
                        Message_Builder.setSpan(new ImageSpan(context, emote, ImageSpan.ALIGN_BOTTOM), Integer.valueOf(index_l[0]), Integer.valueOf(index_l[1]) + 1,0);
                    }
                }
                rightViewHolder.Message.setText(Message_Builder);
            }
            if(badge_bitmaps != null && badge_bitmaps.size() != 0){
                int[] a = {0,2,4,6,8,10};
                for(int i = 0; i < badge_bitmaps.size(); i++){
                    Badge_builder.append("  ");//빈 문자열 2개 넣고
                    Bitmap resized = Bitmap.createScaledBitmap(badge_bitmaps.get(i), textHeight + 10, textHeight + 10, true);
                    Badge_builder.setSpan(new ImageSpan(context, resized, ImageSpan.ALIGN_BASELINE), a[i], a[i] + 1,0);
                }
                Badge_builder.append(Name_builder);
                rightViewHolder.Name.setText(Badge_builder);
            }
        }else if(holder.getItemViewType() == SubScribeViewType){
            Log.i("onBindViewHolder", "SubScribeViewType 실행");
            //구독 정보 채팅
            //(Sent only on sub or resub)
            String month = Items.get(position).getMsg_param_months();
            String sub_plan = Items.get(position).getMsg_param_sub_plan();
            //(Sent only on subgift)
            String recipient_display_name = Items.get(position).getMsg_param_recipient_display_name();
            String recipient_id = Items.get(position).getMsg_param_recipient_id();
            String recipient_user_name = Items.get(position).getMsg_param_recipient_user_name();

            final SubscribeViewHolder subscribeViewHolder = (SubscribeViewHolder)holder;
            final int textHeight = subscribeViewHolder.UserMessage.getLineHeight();
            final SpannableStringBuilder Name_builder = new SpannableStringBuilder();
            final SpannableStringBuilder Badge_builder = new SpannableStringBuilder();
            final SpannableStringBuilder Message_Builder = new SpannableStringBuilder();
            final SpannableStringBuilder SubMsg_Builder = new SpannableStringBuilder();
            Name_builder.append(name);//닉네임
            Name_builder.setSpan(new ForegroundColorSpan(Color.parseColor(color)), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            Name_builder.setSpan(new StyleSpan(Typeface.BOLD),0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            Name_builder.append(" : ");
            if(!message.equals("")){
                Message_Builder.append(message);
                subscribeViewHolder.UserMessage.setVisibility(View.VISIBLE);
                subscribeViewHolder.UserMessage.setText(Name_builder.append(Message_Builder));
            }
            if(month != null && !month.equals("")){
                //sub or resub
                SpannableStringBuilder namebuilder_temp = new SpannableStringBuilder();
                namebuilder_temp.append(name).setSpan(new StyleSpan(Typeface.BOLD),0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                switch (sub_plan){
                    case "Prime":
                        if(month.equals("1")){
                            SubMsg_Builder.append(namebuilder_temp).append(" 님이 방금 트위치 프라임 정기구독을 신청했습니다! ");
                        }else{
                            SubMsg_Builder.append(namebuilder_temp).append(" 님이 방금 트위치 프라임 정기구독을 신청했습니다! ").append(namebuilder_temp).append(" 님이 연속 ").append(month).append("개월 동안 정기구독했습니다!");
                        }
                        break;
                    case "1000":
                        if(month.equals("1")){
                            SubMsg_Builder.append(namebuilder_temp).append(" 님이 방금 티어 1 정기구독을 신청했습니다! ");
                        }else{
                            SubMsg_Builder.append(namebuilder_temp).append(" 님이 방금 티어 1 정기구독을 신청했습니다! ").append(namebuilder_temp).append(" 님이 연속 ").append(month).append("개월 동안 정기구독했습니다!");
                        }
                        break;
                    case "2000":
                        if(month.equals("1")){
                            SubMsg_Builder.append(namebuilder_temp).append(" 님이 방금 티어 2 정기구독을 신청했습니다! ");
                        }else{
                            SubMsg_Builder.append(namebuilder_temp).append(" 님이 방금 티어 2 정기구독을 신청했습니다! ").append(namebuilder_temp).append(" 님이 연속 ").append(month).append("개월 동안 정기구독했습니다!");
                        }
                        break;
                    case "3000":
                        if(month.equals("1")){
                            SubMsg_Builder.append(namebuilder_temp).append(" 님이 방금 티어 3 정기구독을 신청했습니다! ");
                        }else{
                            SubMsg_Builder.append(namebuilder_temp).append(" 님이 방금 티어 3 정기구독을 신청했습니다! ").append(namebuilder_temp).append(" 님이 연속 ").append(month).append("개월 동안 정기구독했습니다!");
                        }
                        break;
                }
                subscribeViewHolder.SubscribeMessage.setText(SubMsg_Builder);
            }else if(sub_plan != null && recipient_display_name != null && !recipient_display_name.equals("")){
                //구독 선물
                SubMsg_Builder.append(name).setSpan(new StyleSpan(Typeface.BOLD),0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                SubMsg_Builder.append(" 님이 ");
                SubMsg_Builder.append(recipient_display_name).setSpan(new StyleSpan(Typeface.BOLD),SubMsg_Builder.length() - recipient_display_name.length(), recipient_display_name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                SubMsg_Builder.append(" 님에게 ");
                switch (sub_plan){
                    case "Prime":
                        SubMsg_Builder.append("프라임 구독을 선물하셨습니다!");
                        break;
                    case "1000":
                        SubMsg_Builder.append("티어 1 구독을 선물하셨습니다!");
                        break;
                    case "2000":
                        SubMsg_Builder.append("티어 2 구독을 선물하셨습니다!");
                        break;
                    case "3000":
                        SubMsg_Builder.append("티어 3 구독을 선물하셨습니다!");
                        break;
                }
                subscribeViewHolder.SubscribeMessage.setText(SubMsg_Builder);
            }

            if(!message.equals("") && emote_bitmaps != null){
                for (Map.Entry<Bitmap,String> entry : emote_bitmaps.entrySet()) {
                    Bitmap emote = entry.getKey();
                    String index = entry.getValue();

                    String[] index_s = index.split(",");
                    for(int j = 0; j < index_s.length; j++){
                        String[] index_l = index_s[j].split("-");
                        Message_Builder.setSpan(new ImageSpan(context, emote, ImageSpan.ALIGN_BOTTOM), Integer.valueOf(index_l[0]), Integer.valueOf(index_l[1]) + 1,0);
                    }
                }
                subscribeViewHolder.UserMessage.setText(Message_Builder);
            }
            if(badge_bitmaps != null && badge_bitmaps.size() != 0){
                int[] a = {0,2,4,6,8,10};
                for(int i = 0; i < badge_bitmaps.size(); i++){
                    Badge_builder.append("  ");//빈 문자열 2개 넣고
                    Bitmap resized = Bitmap.createScaledBitmap(badge_bitmaps.get(i), textHeight + 10, textHeight + 10, true);
                    Badge_builder.setSpan(new ImageSpan(context, resized, ImageSpan.ALIGN_BASELINE), a[i], a[i] + 1,0);
                }
                Badge_builder.append(Name_builder);
                subscribeViewHolder.UserMessage.setText(Badge_builder);
            }
        }else if(holder.getItemViewType() == InfoViewType){
            //Info Chat.
            LeftViewHolder leftViewHolder = (LeftViewHolder)holder;
            leftViewHolder.Message.setText(message);
        }else if(holder.getItemViewType() == BitViewType){
            //Bit Chat
        }
    }

    @Override
    public int getItemViewType(int position) {
        //badge :  ex)   moderator/1,subscriber/6,partner/1 mod : 1
        String viewType = Items.get(position).getViewType();
        if(viewType != null){
            String badges = Items.get(position).getBadges();
            boolean isSpecial = false;
            if(badges != null){
                isSpecial = badges.contains("admin") || badges.contains("broadcaster") ||
                                badges.contains("global_mod") || badges.contains("moderator") || badges.contains("staff");
            }
            if(viewType.equals(context.getString(R.string.Main_Chat))){
                if(isSpecial){
                    return RightBadgeViewType;
                }else{
                    if(badges == null || badges.equals("")){
                        return LeftViewType;
                    }else{
                        return LeftBadgeViewType;
                    }
                }
            }else if(viewType.equals(context.getString(R.string.Alert_Chat))){
                return InfoViewType;
            }else if(viewType.equals(context.getString(R.string.Bit_Chat))){
                return BitViewType;
            }else if(viewType.equals(context.getString(R.string.SubScribe_Chat))){
                return SubScribeViewType;
            }
        }

        return position;
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    @Override
    public void onItemClick(View v, int position) {

    }

    @Override
    public void onItemLongClick(View v, int position) {

    }

    static class LeftViewHolder extends RecyclerView.ViewHolder {
        TextView Message;
        public LeftViewHolder(View itemView, final ItemClickListener itemClickListener) {
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
            Message = itemView.findViewById(R.id.message_l);
        }
    }
    static class RightViewHolder extends RecyclerView.ViewHolder{
        TextView Name;
        TextView Message;
        ImageView Emote_Container_Right;
        public RightViewHolder(View itemView, final ItemClickListener itemClickListener) {
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
            Name = itemView.findViewById(R.id.name_r);
            Message = itemView.findViewById(R.id.message_r);
            Emote_Container_Right = itemView.findViewById(R.id.emote_container_right);
        }
    }
    static class SubscribeViewHolder extends RecyclerView.ViewHolder{
        TextView SubscribeMessage;
        TextView UserMessage;
        public SubscribeViewHolder(View itemView) {
            super(itemView);
            SubscribeMessage = itemView.findViewById(R.id.sub_text_1);
            UserMessage = itemView.findViewById(R.id.sub_text_2);
        }
    }
    public void add_chat(MessageObj messageObj){
        Items.add(messageObj);
        if(!lock){
            rv.smoothScrollToPosition(Items.size() + 1);
        }
        /*if(Items.size() > 500){
            Items.remove(0);
            ((Stream_Player_Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notifyItemRemoved(0);
                }
            });
        }*/
        ((Stream_Player_Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyItemInserted(Items.size());
            }
        });
    }

    public void add_chat_event(String viewType, String CommandType, String message){
        MessageObj messageObj = new MessageObj(
                viewType,message,CommandType
        );
        Items.add(messageObj);
        if(!lock){
            rv.smoothScrollToPosition(Items.size() + 1);
        }
        /*if(Items.size() > 500){
            Items.remove(0);
            ((Stream_Player_Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notifyItemRemoved(0);
                }
            });
        }*/
        ((Stream_Player_Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyItemInserted(Items.size());
            }
        });
    }


    /**
     * Twitch API : CLEARCHAT 명령어에 의해 실행됨.
     * @param target_user_id 삭제 대상 채팅의 유저 id
     * @param msg 삭제 후 표시할 문자열.
     */
    public void DeleteBanMsg(String target_user_id ,String msg){
        for(int i = 0; i < Items.size(); i++){
            if(Items.get(i).getUser_id() != null && Items.get(i).getUser_id().equals(target_user_id)){
                MessageObj messageObj = Items.get(i);
                messageObj.setMessage(msg);
                messageObj.setIsdeleted(true);
                //메시지가 삭제되었으므로 Emote String 도 같이 삭제해야 onBindViewHolder 에서 이모티콘 TextView ImageSpan 설정시 ArrayIndexOutOfBoundsException 에러가 나지 않는다..
                messageObj.setBits("");
                messageObj.setEmote_bitmaps(null);
                final int finalI = i;
                ((Stream_Player_Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyItemChanged(finalI);
                    }
                });
            }
        }
    }
    public void clear(){
        Items.clear();
        ((Stream_Player_Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }
}
