package io.jenkins.blueocean.service.embedded.rest;

import hudson.Functions;
import hudson.model.Run;
import io.jenkins.blueocean.commons.IterableUtils;
import io.jenkins.blueocean.rest.Reachable;
import io.jenkins.blueocean.rest.factory.BlueArtifactFactory;
import io.jenkins.blueocean.rest.hal.Link;
import io.jenkins.blueocean.rest.model.BlueArtifact;
import io.jenkins.blueocean.rest.model.BlueArtifactContainer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

public class ArtifactContainerImpl extends BlueArtifactContainer {
    final private Run run;
    final private Link self;

    public ArtifactContainerImpl(Run r, Reachable parent) {
        this.run = r;
        this.self = parent.getLink().rel("artifacts");
    }

    @Override
    public Link getLink() {
        return self;
    }

    @Override
    public BlueArtifact get(final String name) {
        // Check security for artifacts
        if(Functions.isArtifactsPermissionEnabled() && !run.hasPermission(Run.ARTIFACTS)) {
            return null;
        }
        return StreamSupport.stream( iterable().spliterator(), false).
            filter(input -> input != null && name.equals(input.getId())).
            findFirst().orElse(null);
    }

    @Override
    @Nonnull
    public Iterator<BlueArtifact> iterator() {
        return iterable().iterator();
    }

    Iterable<BlueArtifact> iterable() {
        // Check security for artifacts
        if(Functions.isArtifactsPermissionEnabled() && !run.hasPermission(Run.ARTIFACTS)) {
            return Collections.emptyList();
        }
        return BlueArtifactFactory.resolve(run, this);
    }
}
