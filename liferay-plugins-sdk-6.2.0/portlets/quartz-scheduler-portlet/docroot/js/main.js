YUI().ready('event','node', 'aui-modal', function(Y) {
	var form = Y.one('form');
	var action = form.get('action');
	var table = Y.one('table');
    var tbody = table.one('tbody');
    var tr_list = tbody.all('tr:not(.lfr-template)');
    var index;
    
    //Popup defination
    var modal = new Y.Modal(
    		{
    			bodyContent: Liferay.Language.get('com.rivet.schedule_job.popup.warning.msg.body'),
    			centered: true,
    			headerContent: '<h3>' + Liferay.Language.get('com.rivet.schedule_job.popup.warning.title') + '</h3>',
    			modal: true,
    			render: '#shutdownPopup',
    			width: 350,
    			visible: false
    		}).render();
    
    modal.addToolbar(
    		[
    		 {
    			 id: 'cancel_p',
    			 label: Liferay.Language.get('com.rivet.schedule_job.popup.button.cancel'),
    	         on: {
    	        	 click: function() {
    	        		 modal.hide();
    	        	 }
    	         }
    		 },
    	     {
    			 id:'shutdown',
    			 label: Liferay.Language.get('com.rivet.schedule_job.popup.button.accept')
    		 }
    		]);
    
    //Actions buttons
    Y.delegate('click', function(e) {
    	e.preventDefault();
    	var id = this.get('id');
    	
    	console.log('You clicked '+id+' button.');
    	
    	if(id != 'shutdown_p' && id != 'cancel_p') {
    		if(id != 'refresh') {
		    	tr_list.each(function(tr, i, list) {
		    		var input_check = tr.one('input');
		    	    if(Y.Lang.isObject(input_check)) {
		            if(input_check.get('checked')) {
		            	try{
		            			index = form.one('input[id="index"]').get('value');
		            			index++;
		            			form.one('input[id="index"]').set('value', index);
		            			action += '&jobName_'+ index + '=' + tr.one('input[id="fullJobName"]').get('value');
		            			action += '&groupName_'+ index + '=' + tr.one('input[id="fullGroupName"]').get('value');
		            			action += '&storageType_'+ index + '=' + tr.one('td.last').get('text').trim();
		            		} catch(error) {
		            			console.log(error);
		            			}
		            		}
		            }
		    	});
	    		
	    		action += '&index=' + form.one('input[id="index"]').get('value');
    		}
    		action += '&button='+id;
    		form.set('action', action);
    		form.submit();
    	} else if (id === 'shutdown_p'){
    		modal.show();
    	}
        
    }, '#schedulerJobsContainer', 'button');

});

