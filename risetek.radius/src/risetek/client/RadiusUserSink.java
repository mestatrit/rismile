package risetek.client;

import risetek.client.view.UserView;

import com.risetek.rismile.client.sink.Sink;
import com.risetek.rismile.client.view.RismileTableView;


public class RadiusUserSink extends Sink {
	public static final String Tag = "UserInfo";

	String[] columns = {"序号","IMSI","用户名称","口令","分配地址","状态"};
	String[] columnStyles = {"id","imsi","username","password","ipaddress","status"};
	int rowCount = 20;	
	
	RismileTableView tableView = new UserView(columns, columnStyles, rowCount);
	
	public static SinkInfo init() {
		return new SinkInfo(Tag, "用户", "用户管理."){
		      public Sink createInstance() {
		          return new RadiusUserSink();
		        }
		};
	}
	
	public RadiusUserSink() {
		initWidget(tableView);
	}

	public void onShow() {
		tableView.loadModel();
	}
}
