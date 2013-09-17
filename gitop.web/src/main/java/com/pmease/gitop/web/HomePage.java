package com.pmease.gitop.web;

import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.pmease.commons.editable.EditContext;
import com.pmease.commons.editable.ValidationError;
import com.pmease.commons.wicket.asset.bootstrap.BootstrapResourceReference;
import com.pmease.commons.wicket.editable.EditHelper;
import com.pmease.commons.wicket.editable.RenderContext;

@SuppressWarnings("serial")
public class HomePage extends WebPage {

	private static Bean bean = new Bean();
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		final EditContext<RenderContext> editContext = EditHelper.getContext(bean);
		
		Form<?> form = new Form<Void>("form") {

			@Override
			protected void onSubmit() {
				editContext.validate();
				
				for (ValidationError each: editContext.findValidationErrors()) {
					System.out.println(each);
				}
			}
			
		};
		add(new FeedbackPanel("feedback", new ContainerFeedbackMessageFilter(form)));
		
		editContext.renderForEdit(new RenderContext(form, "editor"));
		
		add(form);
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		
		response.render(JavaScriptHeaderItem.forReference(new BootstrapResourceReference()));
	}
	
}