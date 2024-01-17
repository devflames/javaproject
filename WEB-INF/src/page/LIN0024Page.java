package page;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.activation.FileTypeMap;
import javax.imageio.ImageIO;

import common.Base64.Base64;

public class LIN0024Page extends BasePage {

	public int CHAT_ID;
	public String ID;
	public String TYPE;		// ファイル形式
	public String FILE_NAME;	// ファイル名

	@SuppressWarnings("unchecked")
	public void onExec() {

		try {

			HashMap paraMap = this.getBaseQueryParam();

			this.CHAT_ID = common.util.toNum(this.getParam("cid"));
			this.ID = this.getParam("id");
			this.TYPE = this.getParam("type");
			this.FILE_NAME = this.getParam("file_name");


			String directory = "";
			String contentType = "";
			String outputType = "";
			String suffix = "";

			if( "image".equals(this.TYPE) ){
				directory = "/image/";
				contentType = "image/jpeg";
				outputType = "jpg";
				suffix = ".jpg";

			} else if( "video".equals(this.TYPE)){
				directory = "/video/";
				contentType = "video/mp4";
				outputType = "mp4";
				suffix =".mp4";

			} else if( "audio".equals(this.TYPE)){
				directory = "/audio/";
				contentType = "audio/mp4";
				outputType = "m4a";
				suffix = ".m4a";

			} else if( "file".equals(this.TYPE)){
				directory = "/file/";
				contentType = FileTypeMap.getDefaultFileTypeMap().getContentType( this.FILE_NAME );
				outputType = "";
				suffix = "." + common.util.getSuffix(this.FILE_NAME);

			} else if( "sticker".equals(this.TYPE)){
				directory = "/sticker/";
				contentType = "image/png";
				outputType = "png";
				suffix = ".png";
			}


			Path p = Paths.get("/home/contents/"+ this.ACCOUNT + "/" + common.util.trimStart(this.BASIC_ID, "@") + directory + this.CHAT_ID + "_" + this.ID + suffix);

			OutputStream output = null;

			if( "image".equals(this.TYPE) || "sticker".equals(this.TYPE) ){

				BufferedImage bi = ImageIO.read(new File(p.toString()));
				this.res.setContentType( contentType );
				output = this.res.getOutputStream();
				ImageIO.write(bi, outputType, output);

			// video audio file
			} else {

				this.res.setContentType( contentType );

				// user-agentからブラウザ種別を取得
				int	browserType= common.util.getBrowser( this.req.getHeader("user-agent") );

				switch ( browserType ) {
					case common.util.BROWSER_IE:
						this.res.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode( this.CHAT_ID + "_" + this.ID + suffix,"UTF8" ) + "\"");
						break;

					case common.util.BROWSER_OPERA:
						this.res.setHeader("Content-disposition", "attachment; filename*=utf-8'ja'"+ URLEncoder.encode(this.CHAT_ID + "_" + this.ID + suffix, "UTF8"));
						break;

					case common.util.BROWSER_SAFARI:
						this.res.setHeader("Content-disposition", "attachment; filename=\"" + this.CHAT_ID + "_" + this.ID + suffix + "\"");
						break;

					case common.util.BROWSER_NETSCAPE:
					case common.util.BROWSER_FIREFOX:
					case common.util.BROWSER_CHROME:
						this.res.setHeader("Content-disposition", "attachment; filename=\"=?utf-8?B?" + Base64.encode(this.CHAT_ID + "_" + this.ID + suffix, "UTF8") + "?=\"");
						break;

					default:
						this.res.setHeader("Content-disposition", "attachment; filename=\"" + this.CHAT_ID + "_" + this.ID + suffix + "\"");
						break;
				}

			    // 出力用Streamを取得
				output = this.res.getOutputStream();

				try ( BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(p.toString()))) ){

					byte[] readData = new byte[1024];

					Integer data = 0;
					while((data = bis.read(readData,0,readData.length)) != -1){
						output.write(readData,0,data);
					}
				}
			}

			output.flush();
			output.close();

			this.setPath(null);
			return;

		} catch( Exception e ){
			this.logE(e);
		}
	}
}
