package club.rodong.slitch;

import android.content.res.Resources;
import android.util.Log;

import fi.iki.elonen.NanoHTTPD;

/**
 * Stream by Twitch cannot be gain by video source.
 * instead, The Twitch provides an embedded available on the website.
 * For additional information, see: https://dev.twitch.tv/docs/embed/
 */
public class WebServer extends NanoHTTPD {
    private static final int PORT = 8800;
    public WebServer() {
        super(PORT);
    }

    @Override
    public Response serve(IHTTPSession session) {
        try {
            String channel = session.getHeaders().get("channel");
            Log.i("WebServer",channel);
            String response = "<head>\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, user-scalable=no\">\n" +
                    "  <script src= \"https://player.twitch.tv/js/embed/v1.js\"></script>\n" +
                    "</head>\n" +
                    "<body style=\"margin : 0px\">\n" +
                    "<div id=\"player\"></div>\n" +
                    "\n" +
                    "\n" +
                    "<script type=\"text/javascript\">\n" +
                    "  var options = {\n" +
                    "    width: window.innerWidth,\n" +
                    "    height: window.innerHeight,\n" +
                    "    channel: \""+channel+"\",\n" +
                    "    controls: false,\n" +
                    "    autoplay : true\n" +
                    "  };\n" +
                    "  var player = new Twitch.Player(\"player\", options);\n" +
                    "\n" +
                    "\n" +
                    "  player.addEventListener(\"Twitch.Embed.VIDEO_READY\", () =>{\n" +
                    "    \n" +
                    "  });\n" +
                    "  player.addEventListener(Twitch.Player.ENDED, () => {\n" +
                    "    TwitchPlayer.playerEnded();\n" +
                    "    console.log('ENDED');\n" +
                    "    });\n" +
                    "  player.addEventListener(Twitch.Player.PAUSE, () => {\n" +
                    "    TwitchPlayer.playerPause();\n" +
                    "    console.log('PAUSE');\n" +
                    "    });\n" +
                    "  player.addEventListener(Twitch.Player.PLAY, () => {\n" +
                    "    TwitchPlayer.playerPlay();\n" +
                    "    console.log('PLAY');\n" +
                    "  });\n" +
                    "  player.addEventListener(Twitch.Player.PLAYBACK_BLOCKED, () => {\n" +
                    "    TwitchPlayer.playerPlayback_Blocked();\n" +
                    "    console.log('PLAYBACK_BLOCKED');\n" +
                    "  });\n" +
                    "  player.addEventListener(Twitch.Player.PLAYING, () => {\n" +
                    "    TwitchPlayer.playerPlaying(player.getQualities().find(x => x.group === player.getQuality()).name);\n" +
                    "    console.log('PLAYING');\n" +
                    "    TwitchPlayer.setQualities(JSON.stringify(player.getQualities()));\n" +
                    "    console.log(JSON.stringify(player.getQualities()));\n" +
                    "  });\n" +
                    "  player.addEventListener(Twitch.Player.OFFLINE, () => {\n" +
                    "    TwitchPlayer.playerOffline();\n" +
                    "    console.log('OFFLINE');\n" +
                    "  });\n" +
                    "  player.addEventListener(Twitch.Player.ONLINE, () => {\n" +
                    "    TwitchPlayer.playerOnline();\n" +
                    "    console.log('ONLINE');\n" +
                    "  });\n" +
                    "  player.addEventListener(Twitch.Player.READY, () => {\n" +
                    "    TwitchPlayer.playerReady();\n" +
                    "    player.setMuted(false);" +
                    "    player.setVolume(1);" +
                    "    console.log('READY');\n" +
                    "  });\n" +
                    "function play(){\n" +
                    "    player.play();\n" +
                    "  }\n" +
                    "  function pause(){\n" +
                    "    player.pause();\n" +
                    "  }" +
                    "  window.addEventListener('resize', (event) => {\n" +
                    "    player.setWidth(window.innerWidth);\n" +
                    "    player.setHeight(window.innerHeight);\n" +
                    "  });\n" +
                    "\n" +
                    "</script>\n" +
                    "</body>\n";
            return new NanoHTTPD.Response(Response.Status.OK,"text/html",response);
        } catch (Resources.NotFoundException nfe) {
            return new NanoHTTPD.Response(Response.Status.NOT_FOUND, MIME_PLAINTEXT, "Not Found");
        } catch (Exception ex) {
            return new Response(Response.Status.INTERNAL_ERROR, MIME_HTML, "<html><body><h1>Error</h1>" + ex.toString() + "</body></html>");
        }
    }
}
