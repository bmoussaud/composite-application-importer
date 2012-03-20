package com.xebialabs.deployit.service.importer;

import com.google.common.collect.Maps;
import com.xebialabs.deployit.plugin.api.udm.CompositePackage;
import com.xebialabs.deployit.plugin.api.udm.Version;
import com.xebialabs.deployit.server.api.importer.*;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static com.google.common.io.Resources.getResource;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CompositeApplicationImporterTest {

	private Importer importer = new CompositeApplicationImporter();

	@Test
	public void go() throws Exception {
		ImportSource importSource = new FileSource(new File(getResource("composite-application-1.cad").toURI()), false);
		assertTrue(importer.canHandle(importSource));

		final DefaultImportingContext context = new DefaultImportingContext();
		final PackageInfo packageInfo = importer.preparePackage(importSource, context);

		assertEquals("PetCompositeApp", packageInfo.getApplicationName());
		assertEquals("3.4", packageInfo.getApplicationVersion());


		final ImportedPackage importedPackage = importer.importEntities(packageInfo, context);

		assertEquals("Applications/PetCompositeApp", importedPackage.getApplication().getId());
		assertEquals("Applications/PetCompositeApp/3.4", importedPackage.getVersion().getId());
		final List<Version> versions = ((CompositePackage) importedPackage.getVersion()).getPackages();
		assertEquals(2, versions.size());
		assertEquals("[Applications/PetClinic-Ear/1.0, Applications/PetClinic-Ear/2.0]", versions.toString());
	}


	public class DefaultImportingContext implements ImportingContext {
		private Map<String, Object> ctx = Maps.newHashMap();

		@SuppressWarnings({"unchecked"})
		@Override
		public <T> T getAttribute(String s) {
			return (T) ctx.get(s);
		}

		@Override
		public <T> void setAttribute(String s, T t) {
			ctx.put(s, t);
		}
	}

}
