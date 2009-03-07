package risetek.client.view;

import java.util.ArrayList;
import java.util.List;

import risetek.client.control.IfController;
import risetek.client.dialog.IfEditDialog;
import risetek.client.model.IfModel;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedStackPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.control.ViewAction;
import com.risetek.rismile.client.view.IView;

public class InterfaceView extends Composite implements IView{
	final Button addIfButton = new Button();
	final DecoratedStackPanel ifStackPanel = new DecoratedStackPanel();
	public InterfaceView() {

		final VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setWidth("100%");
		verticalPanel.addStyleName("outer-panel");

		verticalPanel.add(addIfButton);
		addIfButton.setText("添加接口");
		addIfButton.setWidth("3cm");
		addIfButton.addClickListener(new ClickListener(){

			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				List<String> dialerNameList = new ArrayList<String>();
				for(int i = 0; i < ifModelList.size(); i++){
					dialerNameList.add(ifModelList.get(i).getName());
				}
				new IfEditDialog(InterfaceView.this, addIfButton, dialerNameList).show("创建接口");
			}
			
		});
		
		verticalPanel.add(ifStackPanel);
		ifStackPanel.setWidth("100%");
	}

	public IfController ifController = new IfController();
	private List<IfModel> ifModelList = new ArrayList<IfModel>();
	
	protected void onLoad(){
		loadModel();
	}
	
	public void loadModel(){
		ifController.getIf(new IfModelAction());
	}
	
	class IfModelAction extends ViewAction{

		public void onSuccess(Object object) {
			// TODO Auto-generated method stub
			super.onSuccess();
			ifModelList = new ArrayList<IfModel>();
			ifStackPanel.clear();
			if(object instanceof List){
				List list = (List)object; 
				for(int i = 0; i < list.size(); i++){
					Object model = list.get(i);
					if(model instanceof IfModel){
						IfModel ifModel = (IfModel)model;
						HTML title = new HTML(ifModel.getName());
						title.setStyleName("systool-title");
						//HorizontalPanel captionPanel = new HorizontalPanel();
						//captionPanel.setWidth("100%");
						IfModelView ifModelView = new IfModelView(ifModel); 
						//captionPanel.add(title);
						//captionPanel.setCellWidth(title, "80%");
						ifModelView.getEditButton().addClickListener(new EditClickListener(ifModel));
						//captionPanel.add(ifModelView.getDelButton());
						//captionPanel.add(ifModelView.getEditButton());
						ifStackPanel.add(ifModelView, title.toString(), true);
						ifModelList.add(ifModel);
					}
				}
			}
		}
		
	}
	class EditClickListener implements ClickListener{
		private IfModel ifModel;
		public EditClickListener(IfModel ifModel){
			this.ifModel = ifModel;
		}
		public void onClick(Widget sender) {
			// TODO Auto-generated method stub
			new IfEditDialog(InterfaceView.this, ifModel, sender).show("编辑接口");
		}

	}
}
