package io.jenkins.blueocean.service.embedded.rest;

import hudson.model.Run;
import io.jenkins.blueocean.commons.ResourcesUtils;
import io.jenkins.blueocean.rest.factory.BlueRunFactory;
import io.jenkins.blueocean.rest.hal.Link;
import io.jenkins.blueocean.rest.model.BluePipelineNode;
import io.jenkins.blueocean.rest.model.BlueRun;
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.BuildWatcher;
import org.jvnet.hudson.test.JenkinsRule;

import java.net.URL;
import java.util.stream.StreamSupport;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BlueJUnitTestResultTest {
    @ClassRule
    public static BuildWatcher buildWatcher = new BuildWatcher();

    @Rule
    public JenkinsRule j = new JenkinsRule();

    @Test
    public void createsTestResult() throws Exception {
        URL resource = getClass().getResource("BlueJUnitTestResultTest.jenkinsfile");
        String jenkinsFile = ResourcesUtils.toString(resource);

        WorkflowJob p = j.createProject(WorkflowJob.class, "project");
        p.setDefinition(new CpsFlowDefinition(jenkinsFile, false));
        p.save();

        Run r = p.scheduleBuild2(0).waitForStart();
        j.waitUntilNoActivity();

        BlueRun test = BlueRunFactory.getRun(r, () -> new Link("test"));

        Assert.assertEquals( 3, StreamSupport.stream(test.getTests().spliterator(), false).count());

        BluePipelineNode node = mock(BluePipelineNode.class);
        when(node.getId()).thenReturn("6");
    }
}
