package com.apexmob.skink.data;

import com.apexmob.skink.DataManager;
import com.apexmob.skink.OnTextListener;
import com.apexmob.skink.Data;
import com.apexmob.skink.Text;

public class ReadTextListener extends AbstractDataFieldListener implements OnTextListener {
	
	public ReadTextListener(DataManager manager, int fieldId) {
		super(manager, fieldId);
	}

	@Override
	public void onText(Text text) {
		String content = filterContent(text.getContent());
		if (content != null) {
			Data evt = new Data(getFieldId(), content, this);
			getDataEventManager().data(evt);
		}
	}
	
	protected String filterContent(String content) {
		return content;
	}

}
