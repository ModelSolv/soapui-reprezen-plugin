package com.modelsolv.reprezen.soapui.actions;

import java.awt.Dimension;
import java.io.File;
import java.util.List;

import com.eviware.soapui.analytics.Analytics;
import com.eviware.soapui.impl.rest.RestService;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.support.UISupport;
import com.eviware.x.dialogs.Worker;
import com.eviware.x.dialogs.XProgressMonitor;
import com.modelsolv.reprezen.soapui.RepreZenImporter;


public class RepreZenImporterWorker extends Worker.WorkerAdapter {
    private final File reprezenFile;
    private WsdlProject project;

    public RepreZenImporterWorker(File reprezenFile, WsdlProject project) {
        this.reprezenFile = reprezenFile;
        this.project = project;
    }

    public Object construct(XProgressMonitor monitor) {
        try {
            RepreZenImporter importer = new RepreZenImporter(project);
            List<RestService> restServices = importer.importZenModel(reprezenFile);
            RestService restService = null;
            if (!restServices.isEmpty()) {
                UISupport.select(restServices.get(0));
                restService = restServices.get(0);
            }
            Analytics.trackAction("ImportRepreZen");
            return restService;
        } catch (Throwable e) {
			UISupport.showExtendedInfo("Error", "An error of type occured.", e.toString(), new Dimension(200, 50));
        }
        return null;
    }
 
}
