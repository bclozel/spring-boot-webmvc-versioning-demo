package com.example.demo;

import com.vdurmont.semver4j.Semver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.condition.AbstractRequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class ApiVersionedResourceRequestCondition extends AbstractRequestCondition<ApiVersionedResourceRequestCondition> {

    private final static Logger LOG = LoggerFactory.getLogger(ApiVersionedResourceRequestCondition.class);

    private final Set<String> versions;
    public ApiVersionedResourceRequestCondition(String version) {
        this(versionGenerator(version));
    }

    public ApiVersionedResourceRequestCondition(Collection<String> versions) {
        this.versions = Collections.unmodifiableSet(new HashSet<>(versions));
    }

    @Override
    public ApiVersionedResourceRequestCondition combine(ApiVersionedResourceRequestCondition other) {
        LOG.debug("Combining:\n{}\n{}", this, other);
        Set<String> newVersions = new LinkedHashSet<>(this.versions);
        newVersions.addAll(other.versions);
        return new ApiVersionedResourceRequestCondition(newVersions);
    }

    @Override
    public ApiVersionedResourceRequestCondition getMatchingCondition(HttpServletRequest request) {
        final String header = request.getHeader(VersionFilter.HEADER_VERSION);
        LOG.debug("Api-Version header = {}", header);
        if (StringUtils.hasLength(header)) {
            Semver headerVersion = new Semver(header, Semver.SemverType.NPM);
            for (String definedVersion : versions) {
                if (headerVersion.satisfies(definedVersion)) {
                    return this;
                }
            }
            LOG.debug("Unable to find a matching version");
        }
        return null;
    }

    @Override
    protected Collection<?> getContent() {
        return versions;
    }

    @Override
    protected String getToStringInfix() {
        return " && ";
    }

    @Override
    public int compareTo(ApiVersionedResourceRequestCondition other, HttpServletRequest request) {
        return 0;
    }


    private static Set<String> versionGenerator(final String version) {
        HashSet<String> versionRanges = new HashSet<>();

        if (StringUtils.hasText(version)) {
            versionRanges.add(version);
        }

        return versionRanges;
    }
}