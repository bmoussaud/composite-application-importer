package com.xebialabs.deployit.service.importer;

import com.google.common.collect.ImmutableList;
import com.xebialabs.deployit.plugin.api.reflect.Type;
import com.xebialabs.deployit.plugin.api.udm.Application;
import com.xebialabs.deployit.plugin.api.udm.CompositePackage;
import com.xebialabs.deployit.server.api.importer.*;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import static com.xebialabs.deployit.server.api.util.IdGenerator.generateId;

public class CompositeApplicationImporter implements ListableImporter {

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

		Application application = Type.valueOf(Application.class).getDescriptor().newInstance();
		application.setId(packageInfo.getApplicationId());


		final CompositeApplicationDescriptor descriptor = context.getAttribute("descriptor");
		CompositePackage version = Type.valueOf(CompositePackage.class).getDescriptor().newInstance();
		version.setId(generateId(application, packageInfo.getApplicationVersion()));
		version.getPackages().addAll(descriptor.getVersions());
		version.setApplication(application);


		ImportedPackage importedPackage = new ImportedPackage(packageInfo, application, version);
		return importedPackage;
	}

	@Override
	public void cleanUp(PackageInfo packageInfo, ImportingContext context) {

	}

	@Override
	public List<String> list(File directory) {
		final String[] list = directory.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(EXTENSION);
			}
		});
		return ImmutableList.copyOf(list);
	}
}
