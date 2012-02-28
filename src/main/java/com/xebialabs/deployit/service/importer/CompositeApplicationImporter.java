package com.xebialabs.deployit.service.importer;

import com.xebialabs.deployit.plugin.api.udm.Application;
import com.xebialabs.deployit.plugin.api.udm.CompositePackage;
import com.xebialabs.deployit.server.api.importer.*;

import static com.xebialabs.deployit.server.api.util.IdGenerator.generateId;

public class CompositeApplicationImporter implements Importer {

	public static String EXTENSION = ".cad";

	@Override
	public boolean canHandle(ImportSource source) {
		return source.getFile().getAbsolutePath().endsWith(EXTENSION);
	}

	@Override
	public PackageInfo preparePackage(ImportSource source, ImportingContext context) {
		final CompositeApplicationDescriptor descriptor = new CompositeApplicationDescriptor(source.getFile());
		context.setAttribute("descriptor", descriptor);
		PackageInfo packageInfo = new PackageInfo(source);
		packageInfo.setApplicationName(descriptor.getApplication());
		packageInfo.setApplicationVersion(descriptor.getVersion());
		return packageInfo;
	}

	@Override
	public ImportedPackage importEntities(PackageInfo packageInfo, ImportingContext context) {

		Application application = new Application();
		application.setId(packageInfo.getApplicationId());


		final CompositeApplicationDescriptor descriptor = context.getAttribute("descriptor");
		CompositePackage version = new CompositePackage();
		version.setId(generateId(application, packageInfo.getApplicationVersion()));
		version.getPackages().addAll(descriptor.getVersions());
		version.setApplication(application);


		ImportedPackage importedPackage = new ImportedPackage(packageInfo, application, version);
		return importedPackage;
	}

	@Override
	public void cleanUp(PackageInfo packageInfo, ImportingContext context) {

	}
}
