package risetek.client;

import risetek.client.view.BlackUserView;

import com.risetek.rismile.client.sink.Sink;
import com.risetek.rismile.client.view.RismileTableView;


public class RadiusBlackSink extends Sink {
	public static final String Tag = "BlackInfo";

	String[] columns = {"序号","IMSI","用户名称"};
	String[] columnStyles = {"id","imsi","username"};
	int rowCount = 20;	

	RismileTableView tableView = new BlackUserView(columns, columnStyles, rowCount);
	
	public static SinkInfo init() {
		return new SinkInfo(Tag, "不明用户", "不明用户管理."){
		      public Sink createInstance() {
		          return new RadiusBlackSink();
		        }
		};
	}
	
	public RadiusBlackSink() {
		initWidget(tableView);
	}

	public void onShow() {
		tableView.loadModel();
	}
}
