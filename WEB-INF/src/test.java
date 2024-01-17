
import dto.Entity.LineAPI.Webhooks.Webhook;


public class test {

	public static void main(String[] args) {

		String json = "{\"events\":[{\"type\":\"follow\",\"replyToken\":\"4d24ce9995b64ff794040764b0540ba5\",\"source\":{\"userId\":\"Ue64eea7cce4d136165beca248d47c563\",\"type\":\"user\"},\"timestamp\":1488967368634}]}";
		json = "{\"events\":[{\"replyToken\":\"nHuyWiB7yP5Zw52FIkcQobQuGDXCTA\",\"type\":\"message\",\"timestamp\":1462629479859,\"source\":{\"type\":\"user\",\"userId\":\"U206d25c2ea6bd87c17655609a1c37cb8\"},\"message\":{\"id\":\"325708\",\"type\":\"text\",\"text\":\"Hello,world\"}},{\"replyToken\":\"nHuyWiB7yP5Zw52FIkcQobQuGDXCTA\",\"type\":\"follow\",\"timestamp\":1462629479859,\"source\":{\"type\":\"user\",\"userId\":\"U206d25c2ea6bd87c17655609a1c37cb8\"}}]}";

		Webhook obj;
		try {

			obj = Webhook.loadJson(json);
			System.out.println(obj.getClass());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
