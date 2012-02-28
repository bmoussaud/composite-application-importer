package com.xebialabs.deployit.service.importer;

import com.xebialabs.deployit.plugin.api.udm.Version;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static com.google.common.io.Resources.getResource;
import static junit.framework.Assert.assertEquals;

public class CompositeApplicationDescriptorTest {

	private CompositeApplicationDescriptor descriptor;

	@Before
	public void setUp() throws Exception {
		descriptor = new CompositeApplicationDescriptor(new File(getResource("composite-application-1.cad").toURI()));
	}

	@Test
	public void testGetApplication() throws Exception {
		assertEquals("PetCompositeApp", descriptor.getApplication());
	}

	@Test
	public void testGetVersion() throws Exception {
		assertEquals("3.4", descriptor.getVersion());
	}

	@Test
	public void testGetVersions() throws Exception {
		final List<Version> versions = descriptor.getVersions();
		assertEquals("[Applications/PetClinic-Ear/1.0, Applications/PetClinic-Ear/2.0]",versions.toString());
	}
}
