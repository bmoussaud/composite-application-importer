package com.xebialabs.deployit.service.importer;

import com.xebialabs.deployit.plugin.api.udm.CompositePackage;
import com.xebialabs.deployit.plugin.api.udm.Version;
import com.xebialabs.deployit.server.api.importer.*;

import java.util.List;

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
		ImportedPackage importedPackage = new ImportedPackage(packageInfo);

		CompositePackage compositePackage = new CompositePackage();

		final CompositeApplicationDescriptor descriptor = context.getAttribute("descriptor");
		final List<Version> versions = descriptor.getVersions();
		/*for (Version version : versions) {
			importedPackage.addDeployable(version);
		} */
		return importedPackage;
	}

	@Override
	public void cleanUp(PackageInfo packageInfo, ImportingContext context) {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
