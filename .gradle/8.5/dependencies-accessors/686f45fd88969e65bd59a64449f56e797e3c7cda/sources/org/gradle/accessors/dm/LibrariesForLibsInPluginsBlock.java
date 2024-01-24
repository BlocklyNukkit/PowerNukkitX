package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the `libs` extension.
 */
@NonNullApi
public class LibrariesForLibsInPluginsBlock extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final CnLibraryAccessors laccForCnLibraryAccessors = new CnLibraryAccessors(owner);
    private final ComLibraryAccessors laccForComLibraryAccessors = new ComLibraryAccessors(owner);
    private final CommonsLibraryAccessors laccForCommonsLibraryAccessors = new CommonsLibraryAccessors(owner);
    private final FrLibraryAccessors laccForFrLibraryAccessors = new FrLibraryAccessors(owner);
    private final IoLibraryAccessors laccForIoLibraryAccessors = new IoLibraryAccessors(owner);
    private final ItLibraryAccessors laccForItLibraryAccessors = new ItLibraryAccessors(owner);
    private final NetLibraryAccessors laccForNetLibraryAccessors = new NetLibraryAccessors(owner);
    private final OrgLibraryAccessors laccForOrgLibraryAccessors = new OrgLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibsInPluginsBlock(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

    /**
     * Returns the group of libraries at cn
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public CnLibraryAccessors getCn() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return laccForCnLibraryAccessors;
    }

    /**
     * Returns the group of libraries at com
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public ComLibraryAccessors getCom() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return laccForComLibraryAccessors;
    }

    /**
     * Returns the group of libraries at commons
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public CommonsLibraryAccessors getCommons() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return laccForCommonsLibraryAccessors;
    }

    /**
     * Returns the group of libraries at fr
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public FrLibraryAccessors getFr() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return laccForFrLibraryAccessors;
    }

    /**
     * Returns the group of libraries at io
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public IoLibraryAccessors getIo() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return laccForIoLibraryAccessors;
    }

    /**
     * Returns the group of libraries at it
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public ItLibraryAccessors getIt() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return laccForItLibraryAccessors;
    }

    /**
     * Returns the group of libraries at net
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public NetLibraryAccessors getNet() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return laccForNetLibraryAccessors;
    }

    /**
     * Returns the group of libraries at org
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public OrgLibraryAccessors getOrg() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return laccForOrgLibraryAccessors;
    }

    /**
     * Returns the group of versions at versions
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Returns the group of bundles at bundles
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public BundleAccessors getBundles() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return baccForBundleAccessors;
    }

    /**
     * Returns the group of plugins at plugins
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class CnLibraryAccessors extends SubDependencyFactory {
        private final CnPowernukkitxLibraryAccessors laccForCnPowernukkitxLibraryAccessors = new CnPowernukkitxLibraryAccessors(owner);

        public CnLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at cn.powernukkitx
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public CnPowernukkitxLibraryAccessors getPowernukkitx() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForCnPowernukkitxLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class CnPowernukkitxLibraryAccessors extends SubDependencyFactory {
        private final CnPowernukkitxLibdeflateLibraryAccessors laccForCnPowernukkitxLibdeflateLibraryAccessors = new CnPowernukkitxLibdeflateLibraryAccessors(owner);
        private final CnPowernukkitxTerraLibraryAccessors laccForCnPowernukkitxTerraLibraryAccessors = new CnPowernukkitxTerraLibraryAccessors(owner);

        public CnPowernukkitxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at cn.powernukkitx.libdeflate
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public CnPowernukkitxLibdeflateLibraryAccessors getLibdeflate() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForCnPowernukkitxLibdeflateLibraryAccessors;
        }

        /**
         * Returns the group of libraries at cn.powernukkitx.terra
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public CnPowernukkitxTerraLibraryAccessors getTerra() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForCnPowernukkitxTerraLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class CnPowernukkitxLibdeflateLibraryAccessors extends SubDependencyFactory {

        public CnPowernukkitxLibdeflateLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for java (cn.powernukkitx:libdeflate-java)
         * with versionRef 'cn.powernukkitx.libdeflate.java'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getJava() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("cn.powernukkitx.libdeflate.java");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class CnPowernukkitxTerraLibraryAccessors extends SubDependencyFactory {

        public CnPowernukkitxTerraLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for binary (cn.powernukkitx:Terra-Binary)
         * with versionRef 'cn.powernukkitx.terra.binary'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getBinary() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("cn.powernukkitx.terra.binary");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ComLibraryAccessors extends SubDependencyFactory {
        private final ComGithubLibraryAccessors laccForComGithubLibraryAccessors = new ComGithubLibraryAccessors(owner);
        private final ComGoogleLibraryAccessors laccForComGoogleLibraryAccessors = new ComGoogleLibraryAccessors(owner);
        private final ComLmaxLibraryAccessors laccForComLmaxLibraryAccessors = new ComLmaxLibraryAccessors(owner);
        private final ComNimbusdsLibraryAccessors laccForComNimbusdsLibraryAccessors = new ComNimbusdsLibraryAccessors(owner);
        private final ComNukkitxLibraryAccessors laccForComNukkitxLibraryAccessors = new ComNukkitxLibraryAccessors(owner);

        public ComLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at com.github
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public ComGithubLibraryAccessors getGithub() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForComGithubLibraryAccessors;
        }

        /**
         * Returns the group of libraries at com.google
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public ComGoogleLibraryAccessors getGoogle() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForComGoogleLibraryAccessors;
        }

        /**
         * Returns the group of libraries at com.lmax
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public ComLmaxLibraryAccessors getLmax() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForComLmaxLibraryAccessors;
        }

        /**
         * Returns the group of libraries at com.nimbusds
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public ComNimbusdsLibraryAccessors getNimbusds() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForComNimbusdsLibraryAccessors;
        }

        /**
         * Returns the group of libraries at com.nukkitx
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public ComNukkitxLibraryAccessors getNukkitx() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForComNukkitxLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ComGithubLibraryAccessors extends SubDependencyFactory {
        private final ComGithubDaniellansunLibraryAccessors laccForComGithubDaniellansunLibraryAccessors = new ComGithubDaniellansunLibraryAccessors(owner);
        private final ComGithubOshiLibraryAccessors laccForComGithubOshiLibraryAccessors = new ComGithubOshiLibraryAccessors(owner);

        public ComGithubLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at com.github.daniellansun
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public ComGithubDaniellansunLibraryAccessors getDaniellansun() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForComGithubDaniellansunLibraryAccessors;
        }

        /**
         * Returns the group of libraries at com.github.oshi
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public ComGithubOshiLibraryAccessors getOshi() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForComGithubOshiLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ComGithubDaniellansunLibraryAccessors extends SubDependencyFactory {
        private final ComGithubDaniellansunFastLibraryAccessors laccForComGithubDaniellansunFastLibraryAccessors = new ComGithubDaniellansunFastLibraryAccessors(owner);

        public ComGithubDaniellansunLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at com.github.daniellansun.fast
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public ComGithubDaniellansunFastLibraryAccessors getFast() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForComGithubDaniellansunFastLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ComGithubDaniellansunFastLibraryAccessors extends SubDependencyFactory {

        public ComGithubDaniellansunFastLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for reflection (com.github.daniellansun:fast-reflection)
         * with versionRef 'com.github.daniellansun.fast.reflection'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getReflection() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("com.github.daniellansun.fast.reflection");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ComGithubOshiLibraryAccessors extends SubDependencyFactory {
        private final ComGithubOshiOshiLibraryAccessors laccForComGithubOshiOshiLibraryAccessors = new ComGithubOshiOshiLibraryAccessors(owner);

        public ComGithubOshiLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at com.github.oshi.oshi
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public ComGithubOshiOshiLibraryAccessors getOshi() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForComGithubOshiOshiLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ComGithubOshiOshiLibraryAccessors extends SubDependencyFactory {

        public ComGithubOshiOshiLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for core (com.github.oshi:oshi-core)
         * with versionRef 'com.github.oshi.oshi.core'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getCore() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("com.github.oshi.oshi.core");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ComGoogleLibraryAccessors extends SubDependencyFactory {
        private final ComGoogleCodeLibraryAccessors laccForComGoogleCodeLibraryAccessors = new ComGoogleCodeLibraryAccessors(owner);
        private final ComGoogleGuavaLibraryAccessors laccForComGoogleGuavaLibraryAccessors = new ComGoogleGuavaLibraryAccessors(owner);

        public ComGoogleLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at com.google.code
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public ComGoogleCodeLibraryAccessors getCode() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForComGoogleCodeLibraryAccessors;
        }

        /**
         * Returns the group of libraries at com.google.guava
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public ComGoogleGuavaLibraryAccessors getGuava() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForComGoogleGuavaLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ComGoogleCodeLibraryAccessors extends SubDependencyFactory {
        private final ComGoogleCodeFindbugsLibraryAccessors laccForComGoogleCodeFindbugsLibraryAccessors = new ComGoogleCodeFindbugsLibraryAccessors(owner);
        private final ComGoogleCodeGsonLibraryAccessors laccForComGoogleCodeGsonLibraryAccessors = new ComGoogleCodeGsonLibraryAccessors(owner);

        public ComGoogleCodeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at com.google.code.findbugs
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public ComGoogleCodeFindbugsLibraryAccessors getFindbugs() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForComGoogleCodeFindbugsLibraryAccessors;
        }

        /**
         * Returns the group of libraries at com.google.code.gson
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public ComGoogleCodeGsonLibraryAccessors getGson() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForComGoogleCodeGsonLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ComGoogleCodeFindbugsLibraryAccessors extends SubDependencyFactory {

        public ComGoogleCodeFindbugsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for jsr305 (com.google.code.findbugs:jsr305)
         * with versionRef 'com.google.code.findbugs.jsr305'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getJsr305() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("com.google.code.findbugs.jsr305");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ComGoogleCodeGsonLibraryAccessors extends SubDependencyFactory {

        public ComGoogleCodeGsonLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for gson (com.google.code.gson:gson)
         * with versionRef 'com.google.code.gson.gson'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getGson() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("com.google.code.gson.gson");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ComGoogleGuavaLibraryAccessors extends SubDependencyFactory {

        public ComGoogleGuavaLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for guava (com.google.guava:guava)
         * with versionRef 'com.google.guava.guava'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getGuava() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("com.google.guava.guava");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ComLmaxLibraryAccessors extends SubDependencyFactory {

        public ComLmaxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for disruptor (com.lmax:disruptor)
         * with versionRef 'com.lmax.disruptor'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getDisruptor() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("com.lmax.disruptor");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ComNimbusdsLibraryAccessors extends SubDependencyFactory {
        private final ComNimbusdsNimbusLibraryAccessors laccForComNimbusdsNimbusLibraryAccessors = new ComNimbusdsNimbusLibraryAccessors(owner);

        public ComNimbusdsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at com.nimbusds.nimbus
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public ComNimbusdsNimbusLibraryAccessors getNimbus() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForComNimbusdsNimbusLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ComNimbusdsNimbusLibraryAccessors extends SubDependencyFactory {
        private final ComNimbusdsNimbusJoseLibraryAccessors laccForComNimbusdsNimbusJoseLibraryAccessors = new ComNimbusdsNimbusJoseLibraryAccessors(owner);

        public ComNimbusdsNimbusLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at com.nimbusds.nimbus.jose
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public ComNimbusdsNimbusJoseLibraryAccessors getJose() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForComNimbusdsNimbusJoseLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ComNimbusdsNimbusJoseLibraryAccessors extends SubDependencyFactory {

        public ComNimbusdsNimbusJoseLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for jwt (com.nimbusds:nimbus-jose-jwt)
         * with versionRef 'com.nimbusds.nimbus.jose.jwt'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getJwt() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("com.nimbusds.nimbus.jose.jwt");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ComNukkitxLibraryAccessors extends SubDependencyFactory {

        public ComNukkitxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for natives (com.nukkitx:natives)
         * with versionRef 'com.nukkitx.natives'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getNatives() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("com.nukkitx.natives");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class CommonsLibraryAccessors extends SubDependencyFactory {
        private final CommonsIoLibraryAccessors laccForCommonsIoLibraryAccessors = new CommonsIoLibraryAccessors(owner);

        public CommonsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at commons.io
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public CommonsIoLibraryAccessors getIo() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForCommonsIoLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class CommonsIoLibraryAccessors extends SubDependencyFactory {
        private final CommonsIoCommonsLibraryAccessors laccForCommonsIoCommonsLibraryAccessors = new CommonsIoCommonsLibraryAccessors(owner);

        public CommonsIoLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at commons.io.commons
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public CommonsIoCommonsLibraryAccessors getCommons() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForCommonsIoCommonsLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class CommonsIoCommonsLibraryAccessors extends SubDependencyFactory {

        public CommonsIoCommonsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for io (commons-io:commons-io)
         * with versionRef 'commons.io.commons.io'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getIo() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("commons.io.commons.io");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class FrLibraryAccessors extends SubDependencyFactory {
        private final FrInriaLibraryAccessors laccForFrInriaLibraryAccessors = new FrInriaLibraryAccessors(owner);

        public FrLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at fr.inria
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public FrInriaLibraryAccessors getInria() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForFrInriaLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class FrInriaLibraryAccessors extends SubDependencyFactory {
        private final FrInriaGforgeLibraryAccessors laccForFrInriaGforgeLibraryAccessors = new FrInriaGforgeLibraryAccessors(owner);

        public FrInriaLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at fr.inria.gforge
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public FrInriaGforgeLibraryAccessors getGforge() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForFrInriaGforgeLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class FrInriaGforgeLibraryAccessors extends SubDependencyFactory {
        private final FrInriaGforgeSpoonLibraryAccessors laccForFrInriaGforgeSpoonLibraryAccessors = new FrInriaGforgeSpoonLibraryAccessors(owner);

        public FrInriaGforgeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at fr.inria.gforge.spoon
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public FrInriaGforgeSpoonLibraryAccessors getSpoon() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForFrInriaGforgeSpoonLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class FrInriaGforgeSpoonLibraryAccessors extends SubDependencyFactory {
        private final FrInriaGforgeSpoonSpoonLibraryAccessors laccForFrInriaGforgeSpoonSpoonLibraryAccessors = new FrInriaGforgeSpoonSpoonLibraryAccessors(owner);

        public FrInriaGforgeSpoonLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at fr.inria.gforge.spoon.spoon
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public FrInriaGforgeSpoonSpoonLibraryAccessors getSpoon() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForFrInriaGforgeSpoonSpoonLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class FrInriaGforgeSpoonSpoonLibraryAccessors extends SubDependencyFactory {

        public FrInriaGforgeSpoonSpoonLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for core (fr.inria.gforge.spoon:spoon-core)
         * with versionRef 'fr.inria.gforge.spoon.spoon.core'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getCore() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("fr.inria.gforge.spoon.spoon.core");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class IoLibraryAccessors extends SubDependencyFactory {
        private final IoNettyLibraryAccessors laccForIoNettyLibraryAccessors = new IoNettyLibraryAccessors(owner);
        private final IoSentryLibraryAccessors laccForIoSentryLibraryAccessors = new IoSentryLibraryAccessors(owner);

        public IoLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at io.netty
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public IoNettyLibraryAccessors getNetty() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForIoNettyLibraryAccessors;
        }

        /**
         * Returns the group of libraries at io.sentry
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public IoSentryLibraryAccessors getSentry() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForIoSentryLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class IoNettyLibraryAccessors extends SubDependencyFactory {
        private final IoNettyNettyLibraryAccessors laccForIoNettyNettyLibraryAccessors = new IoNettyNettyLibraryAccessors(owner);

        public IoNettyLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at io.netty.netty
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public IoNettyNettyLibraryAccessors getNetty() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForIoNettyNettyLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class IoNettyNettyLibraryAccessors extends SubDependencyFactory {
        private final IoNettyNettyTransportLibraryAccessors laccForIoNettyNettyTransportLibraryAccessors = new IoNettyNettyTransportLibraryAccessors(owner);

        public IoNettyNettyLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at io.netty.netty.transport
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public IoNettyNettyTransportLibraryAccessors getTransport() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForIoNettyNettyTransportLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class IoNettyNettyTransportLibraryAccessors extends SubDependencyFactory {
        private final IoNettyNettyTransportClassesLibraryAccessors laccForIoNettyNettyTransportClassesLibraryAccessors = new IoNettyNettyTransportClassesLibraryAccessors(owner);

        public IoNettyNettyTransportLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at io.netty.netty.transport.classes
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public IoNettyNettyTransportClassesLibraryAccessors getClasses() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForIoNettyNettyTransportClassesLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class IoNettyNettyTransportClassesLibraryAccessors extends SubDependencyFactory {

        public IoNettyNettyTransportClassesLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for epoll (io.netty:netty-transport-classes-epoll)
         * with versionRef 'io.netty.netty.transport.classes.epoll'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getEpoll() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("io.netty.netty.transport.classes.epoll");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class IoSentryLibraryAccessors extends SubDependencyFactory {
        private final IoSentrySentryLibraryAccessors laccForIoSentrySentryLibraryAccessors = new IoSentrySentryLibraryAccessors(owner);

        public IoSentryLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at io.sentry.sentry
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public IoSentrySentryLibraryAccessors getSentry() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForIoSentrySentryLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class IoSentrySentryLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public IoSentrySentryLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for sentry (io.sentry:sentry)
         * with versionRef 'io.sentry.sentry'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> asProvider() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("io.sentry.sentry");
        }

            /**
             * Creates a dependency provider for log4j2 (io.sentry:sentry-log4j2)
         * with versionRef 'io.sentry.sentry.log4j2'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getLog4j2() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("io.sentry.sentry.log4j2");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ItLibraryAccessors extends SubDependencyFactory {
        private final ItUnimiLibraryAccessors laccForItUnimiLibraryAccessors = new ItUnimiLibraryAccessors(owner);

        public ItLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at it.unimi
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public ItUnimiLibraryAccessors getUnimi() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForItUnimiLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ItUnimiLibraryAccessors extends SubDependencyFactory {
        private final ItUnimiDsiLibraryAccessors laccForItUnimiDsiLibraryAccessors = new ItUnimiDsiLibraryAccessors(owner);

        public ItUnimiLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at it.unimi.dsi
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public ItUnimiDsiLibraryAccessors getDsi() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForItUnimiDsiLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ItUnimiDsiLibraryAccessors extends SubDependencyFactory {

        public ItUnimiDsiLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for fastutil (it.unimi.dsi:fastutil)
         * with versionRef 'it.unimi.dsi.fastutil'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getFastutil() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("it.unimi.dsi.fastutil");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class NetLibraryAccessors extends SubDependencyFactory {
        private final NetDaporkchopLibraryAccessors laccForNetDaporkchopLibraryAccessors = new NetDaporkchopLibraryAccessors(owner);
        private final NetMinecrellLibraryAccessors laccForNetMinecrellLibraryAccessors = new NetMinecrellLibraryAccessors(owner);
        private final NetSfLibraryAccessors laccForNetSfLibraryAccessors = new NetSfLibraryAccessors(owner);

        public NetLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at net.daporkchop
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public NetDaporkchopLibraryAccessors getDaporkchop() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForNetDaporkchopLibraryAccessors;
        }

        /**
         * Returns the group of libraries at net.minecrell
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public NetMinecrellLibraryAccessors getMinecrell() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForNetMinecrellLibraryAccessors;
        }

        /**
         * Returns the group of libraries at net.sf
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public NetSfLibraryAccessors getSf() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForNetSfLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class NetDaporkchopLibraryAccessors extends SubDependencyFactory {
        private final NetDaporkchopLeveldbLibraryAccessors laccForNetDaporkchopLeveldbLibraryAccessors = new NetDaporkchopLeveldbLibraryAccessors(owner);

        public NetDaporkchopLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at net.daporkchop.leveldb
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public NetDaporkchopLeveldbLibraryAccessors getLeveldb() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForNetDaporkchopLeveldbLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class NetDaporkchopLeveldbLibraryAccessors extends SubDependencyFactory {
        private final NetDaporkchopLeveldbMcpeLibraryAccessors laccForNetDaporkchopLeveldbMcpeLibraryAccessors = new NetDaporkchopLeveldbMcpeLibraryAccessors(owner);

        public NetDaporkchopLeveldbLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at net.daporkchop.leveldb.mcpe
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public NetDaporkchopLeveldbMcpeLibraryAccessors getMcpe() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForNetDaporkchopLeveldbMcpeLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class NetDaporkchopLeveldbMcpeLibraryAccessors extends SubDependencyFactory {

        public NetDaporkchopLeveldbMcpeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for jni (net.daporkchop:leveldb-mcpe-jni)
         * with versionRef 'net.daporkchop.leveldb.mcpe.jni'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getJni() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("net.daporkchop.leveldb.mcpe.jni");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class NetMinecrellLibraryAccessors extends SubDependencyFactory {

        public NetMinecrellLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for terminalconsoleappender (net.minecrell:terminalconsoleappender)
         * with versionRef 'net.minecrell.terminalconsoleappender'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getTerminalconsoleappender() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("net.minecrell.terminalconsoleappender");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class NetSfLibraryAccessors extends SubDependencyFactory {
        private final NetSfJoptLibraryAccessors laccForNetSfJoptLibraryAccessors = new NetSfJoptLibraryAccessors(owner);

        public NetSfLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at net.sf.jopt
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public NetSfJoptLibraryAccessors getJopt() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForNetSfJoptLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class NetSfJoptLibraryAccessors extends SubDependencyFactory {
        private final NetSfJoptSimpleLibraryAccessors laccForNetSfJoptSimpleLibraryAccessors = new NetSfJoptSimpleLibraryAccessors(owner);

        public NetSfJoptLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at net.sf.jopt.simple
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public NetSfJoptSimpleLibraryAccessors getSimple() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForNetSfJoptSimpleLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class NetSfJoptSimpleLibraryAccessors extends SubDependencyFactory {
        private final NetSfJoptSimpleJoptLibraryAccessors laccForNetSfJoptSimpleJoptLibraryAccessors = new NetSfJoptSimpleJoptLibraryAccessors(owner);

        public NetSfJoptSimpleLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at net.sf.jopt.simple.jopt
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public NetSfJoptSimpleJoptLibraryAccessors getJopt() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForNetSfJoptSimpleJoptLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class NetSfJoptSimpleJoptLibraryAccessors extends SubDependencyFactory {

        public NetSfJoptSimpleJoptLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for simple (net.sf.jopt-simple:jopt-simple)
         * with versionRef 'net.sf.jopt.simple.jopt.simple'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getSimple() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("net.sf.jopt.simple.jopt.simple");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgLibraryAccessors extends SubDependencyFactory {
        private final OrgApacheLibraryAccessors laccForOrgApacheLibraryAccessors = new OrgApacheLibraryAccessors(owner);
        private final OrgBitbucketLibraryAccessors laccForOrgBitbucketLibraryAccessors = new OrgBitbucketLibraryAccessors(owner);
        private final OrgCloudburstmcLibraryAccessors laccForOrgCloudburstmcLibraryAccessors = new OrgCloudburstmcLibraryAccessors(owner);
        private final OrgGraalvmLibraryAccessors laccForOrgGraalvmLibraryAccessors = new OrgGraalvmLibraryAccessors(owner);
        private final OrgJetbrainsLibraryAccessors laccForOrgJetbrainsLibraryAccessors = new OrgJetbrainsLibraryAccessors(owner);
        private final OrgJlineLibraryAccessors laccForOrgJlineLibraryAccessors = new OrgJlineLibraryAccessors(owner);
        private final OrgJunitLibraryAccessors laccForOrgJunitLibraryAccessors = new OrgJunitLibraryAccessors(owner);
        private final OrgLz4LibraryAccessors laccForOrgLz4LibraryAccessors = new OrgLz4LibraryAccessors(owner);
        private final OrgMockitoLibraryAccessors laccForOrgMockitoLibraryAccessors = new OrgMockitoLibraryAccessors(owner);
        private final OrgOpenjdkLibraryAccessors laccForOrgOpenjdkLibraryAccessors = new OrgOpenjdkLibraryAccessors(owner);
        private final OrgOw2LibraryAccessors laccForOrgOw2LibraryAccessors = new OrgOw2LibraryAccessors(owner);
        private final OrgProjectlombokLibraryAccessors laccForOrgProjectlombokLibraryAccessors = new OrgProjectlombokLibraryAccessors(owner);
        private final OrgXerialLibraryAccessors laccForOrgXerialLibraryAccessors = new OrgXerialLibraryAccessors(owner);
        private final OrgYamlLibraryAccessors laccForOrgYamlLibraryAccessors = new OrgYamlLibraryAccessors(owner);

        public OrgLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.apache
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgApacheLibraryAccessors getApache() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgApacheLibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.bitbucket
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgBitbucketLibraryAccessors getBitbucket() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgBitbucketLibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.cloudburstmc
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgCloudburstmcLibraryAccessors getCloudburstmc() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgCloudburstmcLibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.graalvm
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgGraalvmLibraryAccessors getGraalvm() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgGraalvmLibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.jetbrains
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgJetbrainsLibraryAccessors getJetbrains() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgJetbrainsLibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.jline
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgJlineLibraryAccessors getJline() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgJlineLibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.junit
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgJunitLibraryAccessors getJunit() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgJunitLibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.lz4
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgLz4LibraryAccessors getLz4() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgLz4LibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.mockito
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgMockitoLibraryAccessors getMockito() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgMockitoLibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.openjdk
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgOpenjdkLibraryAccessors getOpenjdk() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgOpenjdkLibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.ow2
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgOw2LibraryAccessors getOw2() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgOw2LibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.projectlombok
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgProjectlombokLibraryAccessors getProjectlombok() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgProjectlombokLibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.xerial
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgXerialLibraryAccessors getXerial() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgXerialLibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.yaml
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgYamlLibraryAccessors getYaml() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgYamlLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgApacheLibraryAccessors extends SubDependencyFactory {
        private final OrgApacheLoggingLibraryAccessors laccForOrgApacheLoggingLibraryAccessors = new OrgApacheLoggingLibraryAccessors(owner);

        public OrgApacheLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.apache.logging
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgApacheLoggingLibraryAccessors getLogging() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgApacheLoggingLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgApacheLoggingLibraryAccessors extends SubDependencyFactory {
        private final OrgApacheLoggingLog4jLibraryAccessors laccForOrgApacheLoggingLog4jLibraryAccessors = new OrgApacheLoggingLog4jLibraryAccessors(owner);

        public OrgApacheLoggingLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.apache.logging.log4j
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgApacheLoggingLog4jLibraryAccessors getLog4j() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgApacheLoggingLog4jLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgApacheLoggingLog4jLibraryAccessors extends SubDependencyFactory {
        private final OrgApacheLoggingLog4jLog4jLibraryAccessors laccForOrgApacheLoggingLog4jLog4jLibraryAccessors = new OrgApacheLoggingLog4jLog4jLibraryAccessors(owner);

        public OrgApacheLoggingLog4jLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.apache.logging.log4j.log4j
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgApacheLoggingLog4jLog4jLibraryAccessors getLog4j() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgApacheLoggingLog4jLog4jLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgApacheLoggingLog4jLog4jLibraryAccessors extends SubDependencyFactory {
        private final OrgApacheLoggingLog4jLog4jSlf4j2LibraryAccessors laccForOrgApacheLoggingLog4jLog4jSlf4j2LibraryAccessors = new OrgApacheLoggingLog4jLog4jSlf4j2LibraryAccessors(owner);

        public OrgApacheLoggingLog4jLog4jLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for core (org.apache.logging.log4j:log4j-core)
         * with versionRef 'org.apache.logging.log4j.log4j.core'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getCore() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.apache.logging.log4j.log4j.core");
        }

        /**
         * Returns the group of libraries at org.apache.logging.log4j.log4j.slf4j2
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgApacheLoggingLog4jLog4jSlf4j2LibraryAccessors getSlf4j2() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgApacheLoggingLog4jLog4jSlf4j2LibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgApacheLoggingLog4jLog4jSlf4j2LibraryAccessors extends SubDependencyFactory {

        public OrgApacheLoggingLog4jLog4jSlf4j2LibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for impl (org.apache.logging.log4j:log4j-slf4j2-impl)
         * with versionRef 'org.apache.logging.log4j.log4j.slf4j2.impl'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getImpl() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.apache.logging.log4j.log4j.slf4j2.impl");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgBitbucketLibraryAccessors extends SubDependencyFactory {
        private final OrgBitbucketBLibraryAccessors laccForOrgBitbucketBLibraryAccessors = new OrgBitbucketBLibraryAccessors(owner);

        public OrgBitbucketLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.bitbucket.b
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgBitbucketBLibraryAccessors getB() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgBitbucketBLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgBitbucketBLibraryAccessors extends SubDependencyFactory {
        private final OrgBitbucketBCLibraryAccessors laccForOrgBitbucketBCLibraryAccessors = new OrgBitbucketBCLibraryAccessors(owner);

        public OrgBitbucketBLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.bitbucket.b.c
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgBitbucketBCLibraryAccessors getC() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgBitbucketBCLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgBitbucketBCLibraryAccessors extends SubDependencyFactory {

        public OrgBitbucketBCLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for jose4j (org.bitbucket.b_c:jose4j)
         * with versionRef 'org.bitbucket.b.c.jose4j'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getJose4j() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.bitbucket.b.c.jose4j");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgCloudburstmcLibraryAccessors extends SubDependencyFactory {
        private final OrgCloudburstmcNettyLibraryAccessors laccForOrgCloudburstmcNettyLibraryAccessors = new OrgCloudburstmcNettyLibraryAccessors(owner);

        public OrgCloudburstmcLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.cloudburstmc.netty
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgCloudburstmcNettyLibraryAccessors getNetty() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgCloudburstmcNettyLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgCloudburstmcNettyLibraryAccessors extends SubDependencyFactory {
        private final OrgCloudburstmcNettyNettyLibraryAccessors laccForOrgCloudburstmcNettyNettyLibraryAccessors = new OrgCloudburstmcNettyNettyLibraryAccessors(owner);

        public OrgCloudburstmcNettyLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.cloudburstmc.netty.netty
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgCloudburstmcNettyNettyLibraryAccessors getNetty() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgCloudburstmcNettyNettyLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgCloudburstmcNettyNettyLibraryAccessors extends SubDependencyFactory {
        private final OrgCloudburstmcNettyNettyTransportLibraryAccessors laccForOrgCloudburstmcNettyNettyTransportLibraryAccessors = new OrgCloudburstmcNettyNettyTransportLibraryAccessors(owner);

        public OrgCloudburstmcNettyNettyLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.cloudburstmc.netty.netty.transport
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgCloudburstmcNettyNettyTransportLibraryAccessors getTransport() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgCloudburstmcNettyNettyTransportLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgCloudburstmcNettyNettyTransportLibraryAccessors extends SubDependencyFactory {

        public OrgCloudburstmcNettyNettyTransportLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for raknet (org.cloudburstmc.netty:netty-transport-raknet)
         * with versionRef 'org.cloudburstmc.netty.netty.transport.raknet'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getRaknet() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.cloudburstmc.netty.netty.transport.raknet");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgGraalvmLibraryAccessors extends SubDependencyFactory {
        private final OrgGraalvmJsLibraryAccessors laccForOrgGraalvmJsLibraryAccessors = new OrgGraalvmJsLibraryAccessors(owner);
        private final OrgGraalvmSdkLibraryAccessors laccForOrgGraalvmSdkLibraryAccessors = new OrgGraalvmSdkLibraryAccessors(owner);
        private final OrgGraalvmToolsLibraryAccessors laccForOrgGraalvmToolsLibraryAccessors = new OrgGraalvmToolsLibraryAccessors(owner);

        public OrgGraalvmLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.graalvm.js
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgGraalvmJsLibraryAccessors getJs() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgGraalvmJsLibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.graalvm.sdk
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgGraalvmSdkLibraryAccessors getSdk() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgGraalvmSdkLibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.graalvm.tools
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgGraalvmToolsLibraryAccessors getTools() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgGraalvmToolsLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgGraalvmJsLibraryAccessors extends SubDependencyFactory {
        private final OrgGraalvmJsJsLibraryAccessors laccForOrgGraalvmJsJsLibraryAccessors = new OrgGraalvmJsJsLibraryAccessors(owner);

        public OrgGraalvmJsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.graalvm.js.js
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgGraalvmJsJsLibraryAccessors getJs() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgGraalvmJsJsLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgGraalvmJsJsLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public OrgGraalvmJsJsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for js (org.graalvm.js:js)
         * with versionRef 'org.graalvm.js.js'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> asProvider() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.graalvm.js.js");
        }

            /**
             * Creates a dependency provider for scriptengine (org.graalvm.js:js-scriptengine)
         * with versionRef 'org.graalvm.js.js.scriptengine'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getScriptengine() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.graalvm.js.js.scriptengine");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgGraalvmSdkLibraryAccessors extends SubDependencyFactory {
        private final OrgGraalvmSdkGraalLibraryAccessors laccForOrgGraalvmSdkGraalLibraryAccessors = new OrgGraalvmSdkGraalLibraryAccessors(owner);

        public OrgGraalvmSdkLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.graalvm.sdk.graal
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgGraalvmSdkGraalLibraryAccessors getGraal() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgGraalvmSdkGraalLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgGraalvmSdkGraalLibraryAccessors extends SubDependencyFactory {

        public OrgGraalvmSdkGraalLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for sdk (org.graalvm.sdk:graal-sdk)
         * with versionRef 'org.graalvm.sdk.graal.sdk'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getSdk() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.graalvm.sdk.graal.sdk");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgGraalvmToolsLibraryAccessors extends SubDependencyFactory {

        public OrgGraalvmToolsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for chromeinspector (org.graalvm.tools:chromeinspector)
         * with versionRef 'org.graalvm.tools.chromeinspector'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getChromeinspector() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.graalvm.tools.chromeinspector");
        }

            /**
             * Creates a dependency provider for profiler (org.graalvm.tools:profiler)
         * with versionRef 'org.graalvm.tools.profiler'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getProfiler() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.graalvm.tools.profiler");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgJetbrainsLibraryAccessors extends SubDependencyFactory {

        public OrgJetbrainsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for annotations (org.jetbrains:annotations)
         * with versionRef 'org.jetbrains.annotations'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getAnnotations() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.jetbrains.annotations");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgJlineLibraryAccessors extends SubDependencyFactory {
        private final OrgJlineJlineLibraryAccessors laccForOrgJlineJlineLibraryAccessors = new OrgJlineJlineLibraryAccessors(owner);

        public OrgJlineLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.jline.jline
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgJlineJlineLibraryAccessors getJline() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgJlineJlineLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgJlineJlineLibraryAccessors extends SubDependencyFactory {
        private final OrgJlineJlineTerminalLibraryAccessors laccForOrgJlineJlineTerminalLibraryAccessors = new OrgJlineJlineTerminalLibraryAccessors(owner);

        public OrgJlineJlineLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for reader (org.jline:jline-reader)
         * with versionRef 'org.jline.jline.reader'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getReader() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.jline.jline.reader");
        }

        /**
         * Returns the group of libraries at org.jline.jline.terminal
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgJlineJlineTerminalLibraryAccessors getTerminal() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgJlineJlineTerminalLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgJlineJlineTerminalLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public OrgJlineJlineTerminalLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for terminal (org.jline:jline-terminal)
         * with versionRef 'org.jline.jline.terminal'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> asProvider() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.jline.jline.terminal");
        }

            /**
             * Creates a dependency provider for jna (org.jline:jline-terminal-jna)
         * with versionRef 'org.jline.jline.terminal.jna'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getJna() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.jline.jline.terminal.jna");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgJunitLibraryAccessors extends SubDependencyFactory {
        private final OrgJunitJupiterLibraryAccessors laccForOrgJunitJupiterLibraryAccessors = new OrgJunitJupiterLibraryAccessors(owner);

        public OrgJunitLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.junit.jupiter
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgJunitJupiterLibraryAccessors getJupiter() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgJunitJupiterLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgJunitJupiterLibraryAccessors extends SubDependencyFactory {
        private final OrgJunitJupiterJunitLibraryAccessors laccForOrgJunitJupiterJunitLibraryAccessors = new OrgJunitJupiterJunitLibraryAccessors(owner);

        public OrgJunitJupiterLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.junit.jupiter.junit
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgJunitJupiterJunitLibraryAccessors getJunit() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgJunitJupiterJunitLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgJunitJupiterJunitLibraryAccessors extends SubDependencyFactory {

        public OrgJunitJupiterJunitLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for jupiter (org.junit.jupiter:junit-jupiter)
         * with versionRef 'org.junit.jupiter.junit.jupiter'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getJupiter() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.junit.jupiter.junit.jupiter");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgLz4LibraryAccessors extends SubDependencyFactory {
        private final OrgLz4Lz4LibraryAccessors laccForOrgLz4Lz4LibraryAccessors = new OrgLz4Lz4LibraryAccessors(owner);

        public OrgLz4LibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.lz4.lz4
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgLz4Lz4LibraryAccessors getLz4() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgLz4Lz4LibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgLz4Lz4LibraryAccessors extends SubDependencyFactory {

        public OrgLz4Lz4LibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for java (org.lz4:lz4-java)
         * with versionRef 'org.lz4.lz4.java'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getJava() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.lz4.lz4.java");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgMockitoLibraryAccessors extends SubDependencyFactory {
        private final OrgMockitoMockitoLibraryAccessors laccForOrgMockitoMockitoLibraryAccessors = new OrgMockitoMockitoLibraryAccessors(owner);

        public OrgMockitoLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.mockito.mockito
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgMockitoMockitoLibraryAccessors getMockito() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgMockitoMockitoLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgMockitoMockitoLibraryAccessors extends SubDependencyFactory {
        private final OrgMockitoMockitoJunitLibraryAccessors laccForOrgMockitoMockitoJunitLibraryAccessors = new OrgMockitoMockitoJunitLibraryAccessors(owner);

        public OrgMockitoMockitoLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.mockito.mockito.junit
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgMockitoMockitoJunitLibraryAccessors getJunit() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgMockitoMockitoJunitLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgMockitoMockitoJunitLibraryAccessors extends SubDependencyFactory {

        public OrgMockitoMockitoJunitLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for jupiter (org.mockito:mockito-junit-jupiter)
         * with versionRef 'org.mockito.mockito.junit.jupiter'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getJupiter() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.mockito.mockito.junit.jupiter");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgOpenjdkLibraryAccessors extends SubDependencyFactory {
        private final OrgOpenjdkJmhLibraryAccessors laccForOrgOpenjdkJmhLibraryAccessors = new OrgOpenjdkJmhLibraryAccessors(owner);

        public OrgOpenjdkLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.openjdk.jmh
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgOpenjdkJmhLibraryAccessors getJmh() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgOpenjdkJmhLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgOpenjdkJmhLibraryAccessors extends SubDependencyFactory {
        private final OrgOpenjdkJmhJmhLibraryAccessors laccForOrgOpenjdkJmhJmhLibraryAccessors = new OrgOpenjdkJmhJmhLibraryAccessors(owner);

        public OrgOpenjdkJmhLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.openjdk.jmh.jmh
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgOpenjdkJmhJmhLibraryAccessors getJmh() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgOpenjdkJmhJmhLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgOpenjdkJmhJmhLibraryAccessors extends SubDependencyFactory {
        private final OrgOpenjdkJmhJmhGeneratorLibraryAccessors laccForOrgOpenjdkJmhJmhGeneratorLibraryAccessors = new OrgOpenjdkJmhJmhGeneratorLibraryAccessors(owner);

        public OrgOpenjdkJmhJmhLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for core (org.openjdk.jmh:jmh-core)
         * with versionRef 'org.openjdk.jmh.jmh.core'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getCore() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.openjdk.jmh.jmh.core");
        }

        /**
         * Returns the group of libraries at org.openjdk.jmh.jmh.generator
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgOpenjdkJmhJmhGeneratorLibraryAccessors getGenerator() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgOpenjdkJmhJmhGeneratorLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgOpenjdkJmhJmhGeneratorLibraryAccessors extends SubDependencyFactory {

        public OrgOpenjdkJmhJmhGeneratorLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for annprocess (org.openjdk.jmh:jmh-generator-annprocess)
         * with versionRef 'org.openjdk.jmh.jmh.generator.annprocess'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getAnnprocess() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.openjdk.jmh.jmh.generator.annprocess");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgOw2LibraryAccessors extends SubDependencyFactory {
        private final OrgOw2AsmLibraryAccessors laccForOrgOw2AsmLibraryAccessors = new OrgOw2AsmLibraryAccessors(owner);

        public OrgOw2LibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.ow2.asm
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgOw2AsmLibraryAccessors getAsm() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgOw2AsmLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgOw2AsmLibraryAccessors extends SubDependencyFactory {

        public OrgOw2AsmLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for asm (org.ow2.asm:asm)
         * with versionRef 'org.ow2.asm.asm'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getAsm() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.ow2.asm.asm");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgProjectlombokLibraryAccessors extends SubDependencyFactory {

        public OrgProjectlombokLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for lombok (org.projectlombok:lombok)
         * with versionRef 'org.projectlombok.lombok'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getLombok() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.projectlombok.lombok");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgXerialLibraryAccessors extends SubDependencyFactory {
        private final OrgXerialSnappyLibraryAccessors laccForOrgXerialSnappyLibraryAccessors = new OrgXerialSnappyLibraryAccessors(owner);

        public OrgXerialLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.xerial.snappy
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgXerialSnappyLibraryAccessors getSnappy() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgXerialSnappyLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgXerialSnappyLibraryAccessors extends SubDependencyFactory {
        private final OrgXerialSnappySnappyLibraryAccessors laccForOrgXerialSnappySnappyLibraryAccessors = new OrgXerialSnappySnappyLibraryAccessors(owner);

        public OrgXerialSnappyLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.xerial.snappy.snappy
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public OrgXerialSnappySnappyLibraryAccessors getSnappy() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForOrgXerialSnappySnappyLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgXerialSnappySnappyLibraryAccessors extends SubDependencyFactory {

        public OrgXerialSnappySnappyLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for java (org.xerial.snappy:snappy-java)
         * with versionRef 'org.xerial.snappy.snappy.java'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getJava() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.xerial.snappy.snappy.java");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class OrgYamlLibraryAccessors extends SubDependencyFactory {

        public OrgYamlLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for snakeyaml (org.yaml:snakeyaml)
         * with versionRef 'org.yaml.snakeyaml'.
             * This dependency was declared in catalog libs.versions.toml
         * @deprecated Will be removed in Gradle 9.0.
             */
        @Deprecated
            public Provider<MinimalExternalModuleDependency> getSnakeyaml() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
                return create("org.yaml.snakeyaml");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        private final CnVersionAccessors vaccForCnVersionAccessors = new CnVersionAccessors(providers, config);
        private final ComVersionAccessors vaccForComVersionAccessors = new ComVersionAccessors(providers, config);
        private final CommonsVersionAccessors vaccForCommonsVersionAccessors = new CommonsVersionAccessors(providers, config);
        private final FrVersionAccessors vaccForFrVersionAccessors = new FrVersionAccessors(providers, config);
        private final IoVersionAccessors vaccForIoVersionAccessors = new IoVersionAccessors(providers, config);
        private final ItVersionAccessors vaccForItVersionAccessors = new ItVersionAccessors(providers, config);
        private final NetVersionAccessors vaccForNetVersionAccessors = new NetVersionAccessors(providers, config);
        private final OrgVersionAccessors vaccForOrgVersionAccessors = new OrgVersionAccessors(providers, config);
        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.cn
         */
        public CnVersionAccessors getCn() {
            return vaccForCnVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.com
         */
        public ComVersionAccessors getCom() {
            return vaccForComVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.commons
         */
        public CommonsVersionAccessors getCommons() {
            return vaccForCommonsVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.fr
         */
        public FrVersionAccessors getFr() {
            return vaccForFrVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.io
         */
        public IoVersionAccessors getIo() {
            return vaccForIoVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.it
         */
        public ItVersionAccessors getIt() {
            return vaccForItVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.net
         */
        public NetVersionAccessors getNet() {
            return vaccForNetVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org
         */
        public OrgVersionAccessors getOrg() {
            return vaccForOrgVersionAccessors;
        }

    }

    public static class CnVersionAccessors extends VersionFactory  {

        private final CnPowernukkitxVersionAccessors vaccForCnPowernukkitxVersionAccessors = new CnPowernukkitxVersionAccessors(providers, config);
        public CnVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.cn.powernukkitx
         */
        public CnPowernukkitxVersionAccessors getPowernukkitx() {
            return vaccForCnPowernukkitxVersionAccessors;
        }

    }

    public static class CnPowernukkitxVersionAccessors extends VersionFactory  {

        private final CnPowernukkitxLibdeflateVersionAccessors vaccForCnPowernukkitxLibdeflateVersionAccessors = new CnPowernukkitxLibdeflateVersionAccessors(providers, config);
        private final CnPowernukkitxTerraVersionAccessors vaccForCnPowernukkitxTerraVersionAccessors = new CnPowernukkitxTerraVersionAccessors(providers, config);
        public CnPowernukkitxVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.cn.powernukkitx.libdeflate
         */
        public CnPowernukkitxLibdeflateVersionAccessors getLibdeflate() {
            return vaccForCnPowernukkitxLibdeflateVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.cn.powernukkitx.terra
         */
        public CnPowernukkitxTerraVersionAccessors getTerra() {
            return vaccForCnPowernukkitxTerraVersionAccessors;
        }

    }

    public static class CnPowernukkitxLibdeflateVersionAccessors extends VersionFactory  {

        public CnPowernukkitxLibdeflateVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: cn.powernukkitx.libdeflate.java (0.0.2-PNX)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getJava() { return getVersion("cn.powernukkitx.libdeflate.java"); }

    }

    public static class CnPowernukkitxTerraVersionAccessors extends VersionFactory  {

        public CnPowernukkitxTerraVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: cn.powernukkitx.terra.binary (6.3.0-BETA-CONFIG-1.3.3)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getBinary() { return getVersion("cn.powernukkitx.terra.binary"); }

    }

    public static class ComVersionAccessors extends VersionFactory  {

        private final ComGithubVersionAccessors vaccForComGithubVersionAccessors = new ComGithubVersionAccessors(providers, config);
        private final ComGoogleVersionAccessors vaccForComGoogleVersionAccessors = new ComGoogleVersionAccessors(providers, config);
        private final ComLmaxVersionAccessors vaccForComLmaxVersionAccessors = new ComLmaxVersionAccessors(providers, config);
        private final ComNimbusdsVersionAccessors vaccForComNimbusdsVersionAccessors = new ComNimbusdsVersionAccessors(providers, config);
        private final ComNukkitxVersionAccessors vaccForComNukkitxVersionAccessors = new ComNukkitxVersionAccessors(providers, config);
        public ComVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.com.github
         */
        public ComGithubVersionAccessors getGithub() {
            return vaccForComGithubVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.com.google
         */
        public ComGoogleVersionAccessors getGoogle() {
            return vaccForComGoogleVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.com.lmax
         */
        public ComLmaxVersionAccessors getLmax() {
            return vaccForComLmaxVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.com.nimbusds
         */
        public ComNimbusdsVersionAccessors getNimbusds() {
            return vaccForComNimbusdsVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.com.nukkitx
         */
        public ComNukkitxVersionAccessors getNukkitx() {
            return vaccForComNukkitxVersionAccessors;
        }

    }

    public static class ComGithubVersionAccessors extends VersionFactory  {

        private final ComGithubDaniellansunVersionAccessors vaccForComGithubDaniellansunVersionAccessors = new ComGithubDaniellansunVersionAccessors(providers, config);
        private final ComGithubOshiVersionAccessors vaccForComGithubOshiVersionAccessors = new ComGithubOshiVersionAccessors(providers, config);
        public ComGithubVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.com.github.daniellansun
         */
        public ComGithubDaniellansunVersionAccessors getDaniellansun() {
            return vaccForComGithubDaniellansunVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.com.github.oshi
         */
        public ComGithubOshiVersionAccessors getOshi() {
            return vaccForComGithubOshiVersionAccessors;
        }

    }

    public static class ComGithubDaniellansunVersionAccessors extends VersionFactory  {

        private final ComGithubDaniellansunFastVersionAccessors vaccForComGithubDaniellansunFastVersionAccessors = new ComGithubDaniellansunFastVersionAccessors(providers, config);
        public ComGithubDaniellansunVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.com.github.daniellansun.fast
         */
        public ComGithubDaniellansunFastVersionAccessors getFast() {
            return vaccForComGithubDaniellansunFastVersionAccessors;
        }

    }

    public static class ComGithubDaniellansunFastVersionAccessors extends VersionFactory  {

        public ComGithubDaniellansunFastVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: com.github.daniellansun.fast.reflection (08ec134a5c)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getReflection() { return getVersion("com.github.daniellansun.fast.reflection"); }

    }

    public static class ComGithubOshiVersionAccessors extends VersionFactory  {

        private final ComGithubOshiOshiVersionAccessors vaccForComGithubOshiOshiVersionAccessors = new ComGithubOshiOshiVersionAccessors(providers, config);
        public ComGithubOshiVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.com.github.oshi.oshi
         */
        public ComGithubOshiOshiVersionAccessors getOshi() {
            return vaccForComGithubOshiOshiVersionAccessors;
        }

    }

    public static class ComGithubOshiOshiVersionAccessors extends VersionFactory  {

        public ComGithubOshiOshiVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: com.github.oshi.oshi.core (6.4.10)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getCore() { return getVersion("com.github.oshi.oshi.core"); }

    }

    public static class ComGoogleVersionAccessors extends VersionFactory  {

        private final ComGoogleCodeVersionAccessors vaccForComGoogleCodeVersionAccessors = new ComGoogleCodeVersionAccessors(providers, config);
        private final ComGoogleGuavaVersionAccessors vaccForComGoogleGuavaVersionAccessors = new ComGoogleGuavaVersionAccessors(providers, config);
        public ComGoogleVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.com.google.code
         */
        public ComGoogleCodeVersionAccessors getCode() {
            return vaccForComGoogleCodeVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.com.google.guava
         */
        public ComGoogleGuavaVersionAccessors getGuava() {
            return vaccForComGoogleGuavaVersionAccessors;
        }

    }

    public static class ComGoogleCodeVersionAccessors extends VersionFactory  {

        private final ComGoogleCodeFindbugsVersionAccessors vaccForComGoogleCodeFindbugsVersionAccessors = new ComGoogleCodeFindbugsVersionAccessors(providers, config);
        private final ComGoogleCodeGsonVersionAccessors vaccForComGoogleCodeGsonVersionAccessors = new ComGoogleCodeGsonVersionAccessors(providers, config);
        public ComGoogleCodeVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.com.google.code.findbugs
         */
        public ComGoogleCodeFindbugsVersionAccessors getFindbugs() {
            return vaccForComGoogleCodeFindbugsVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.com.google.code.gson
         */
        public ComGoogleCodeGsonVersionAccessors getGson() {
            return vaccForComGoogleCodeGsonVersionAccessors;
        }

    }

    public static class ComGoogleCodeFindbugsVersionAccessors extends VersionFactory  {

        public ComGoogleCodeFindbugsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: com.google.code.findbugs.jsr305 (3.0.2)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getJsr305() { return getVersion("com.google.code.findbugs.jsr305"); }

    }

    public static class ComGoogleCodeGsonVersionAccessors extends VersionFactory  {

        public ComGoogleCodeGsonVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: com.google.code.gson.gson (2.10.1)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getGson() { return getVersion("com.google.code.gson.gson"); }

    }

    public static class ComGoogleGuavaVersionAccessors extends VersionFactory  {

        public ComGoogleGuavaVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: com.google.guava.guava (33.0.0-jre)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getGuava() { return getVersion("com.google.guava.guava"); }

    }

    public static class ComLmaxVersionAccessors extends VersionFactory  {

        public ComLmaxVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: com.lmax.disruptor (3.4.4)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getDisruptor() { return getVersion("com.lmax.disruptor"); }

    }

    public static class ComNimbusdsVersionAccessors extends VersionFactory  {

        private final ComNimbusdsNimbusVersionAccessors vaccForComNimbusdsNimbusVersionAccessors = new ComNimbusdsNimbusVersionAccessors(providers, config);
        public ComNimbusdsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.com.nimbusds.nimbus
         */
        public ComNimbusdsNimbusVersionAccessors getNimbus() {
            return vaccForComNimbusdsNimbusVersionAccessors;
        }

    }

    public static class ComNimbusdsNimbusVersionAccessors extends VersionFactory  {

        private final ComNimbusdsNimbusJoseVersionAccessors vaccForComNimbusdsNimbusJoseVersionAccessors = new ComNimbusdsNimbusJoseVersionAccessors(providers, config);
        public ComNimbusdsNimbusVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.com.nimbusds.nimbus.jose
         */
        public ComNimbusdsNimbusJoseVersionAccessors getJose() {
            return vaccForComNimbusdsNimbusJoseVersionAccessors;
        }

    }

    public static class ComNimbusdsNimbusJoseVersionAccessors extends VersionFactory  {

        public ComNimbusdsNimbusJoseVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: com.nimbusds.nimbus.jose.jwt (9.13)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getJwt() { return getVersion("com.nimbusds.nimbus.jose.jwt"); }

    }

    public static class ComNukkitxVersionAccessors extends VersionFactory  {

        public ComNukkitxVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: com.nukkitx.natives (1.0.3)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getNatives() { return getVersion("com.nukkitx.natives"); }

    }

    public static class CommonsVersionAccessors extends VersionFactory  {

        private final CommonsIoVersionAccessors vaccForCommonsIoVersionAccessors = new CommonsIoVersionAccessors(providers, config);
        public CommonsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.commons.io
         */
        public CommonsIoVersionAccessors getIo() {
            return vaccForCommonsIoVersionAccessors;
        }

    }

    public static class CommonsIoVersionAccessors extends VersionFactory  {

        private final CommonsIoCommonsVersionAccessors vaccForCommonsIoCommonsVersionAccessors = new CommonsIoCommonsVersionAccessors(providers, config);
        public CommonsIoVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.commons.io.commons
         */
        public CommonsIoCommonsVersionAccessors getCommons() {
            return vaccForCommonsIoCommonsVersionAccessors;
        }

    }

    public static class CommonsIoCommonsVersionAccessors extends VersionFactory  {

        public CommonsIoCommonsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: commons.io.commons.io (2.15.1)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getIo() { return getVersion("commons.io.commons.io"); }

    }

    public static class FrVersionAccessors extends VersionFactory  {

        private final FrInriaVersionAccessors vaccForFrInriaVersionAccessors = new FrInriaVersionAccessors(providers, config);
        public FrVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.fr.inria
         */
        public FrInriaVersionAccessors getInria() {
            return vaccForFrInriaVersionAccessors;
        }

    }

    public static class FrInriaVersionAccessors extends VersionFactory  {

        private final FrInriaGforgeVersionAccessors vaccForFrInriaGforgeVersionAccessors = new FrInriaGforgeVersionAccessors(providers, config);
        public FrInriaVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.fr.inria.gforge
         */
        public FrInriaGforgeVersionAccessors getGforge() {
            return vaccForFrInriaGforgeVersionAccessors;
        }

    }

    public static class FrInriaGforgeVersionAccessors extends VersionFactory  {

        private final FrInriaGforgeSpoonVersionAccessors vaccForFrInriaGforgeSpoonVersionAccessors = new FrInriaGforgeSpoonVersionAccessors(providers, config);
        public FrInriaGforgeVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.fr.inria.gforge.spoon
         */
        public FrInriaGforgeSpoonVersionAccessors getSpoon() {
            return vaccForFrInriaGforgeSpoonVersionAccessors;
        }

    }

    public static class FrInriaGforgeSpoonVersionAccessors extends VersionFactory  {

        private final FrInriaGforgeSpoonSpoonVersionAccessors vaccForFrInriaGforgeSpoonSpoonVersionAccessors = new FrInriaGforgeSpoonSpoonVersionAccessors(providers, config);
        public FrInriaGforgeSpoonVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.fr.inria.gforge.spoon.spoon
         */
        public FrInriaGforgeSpoonSpoonVersionAccessors getSpoon() {
            return vaccForFrInriaGforgeSpoonSpoonVersionAccessors;
        }

    }

    public static class FrInriaGforgeSpoonSpoonVersionAccessors extends VersionFactory  {

        public FrInriaGforgeSpoonSpoonVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: fr.inria.gforge.spoon.spoon.core (9.1.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getCore() { return getVersion("fr.inria.gforge.spoon.spoon.core"); }

    }

    public static class IoVersionAccessors extends VersionFactory  {

        private final IoNettyVersionAccessors vaccForIoNettyVersionAccessors = new IoNettyVersionAccessors(providers, config);
        private final IoSentryVersionAccessors vaccForIoSentryVersionAccessors = new IoSentryVersionAccessors(providers, config);
        public IoVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.io.netty
         */
        public IoNettyVersionAccessors getNetty() {
            return vaccForIoNettyVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.io.sentry
         */
        public IoSentryVersionAccessors getSentry() {
            return vaccForIoSentryVersionAccessors;
        }

    }

    public static class IoNettyVersionAccessors extends VersionFactory  {

        private final IoNettyNettyVersionAccessors vaccForIoNettyNettyVersionAccessors = new IoNettyNettyVersionAccessors(providers, config);
        public IoNettyVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.io.netty.netty
         */
        public IoNettyNettyVersionAccessors getNetty() {
            return vaccForIoNettyNettyVersionAccessors;
        }

    }

    public static class IoNettyNettyVersionAccessors extends VersionFactory  {

        private final IoNettyNettyTransportVersionAccessors vaccForIoNettyNettyTransportVersionAccessors = new IoNettyNettyTransportVersionAccessors(providers, config);
        public IoNettyNettyVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.io.netty.netty.transport
         */
        public IoNettyNettyTransportVersionAccessors getTransport() {
            return vaccForIoNettyNettyTransportVersionAccessors;
        }

    }

    public static class IoNettyNettyTransportVersionAccessors extends VersionFactory  {

        private final IoNettyNettyTransportClassesVersionAccessors vaccForIoNettyNettyTransportClassesVersionAccessors = new IoNettyNettyTransportClassesVersionAccessors(providers, config);
        public IoNettyNettyTransportVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.io.netty.netty.transport.classes
         */
        public IoNettyNettyTransportClassesVersionAccessors getClasses() {
            return vaccForIoNettyNettyTransportClassesVersionAccessors;
        }

    }

    public static class IoNettyNettyTransportClassesVersionAccessors extends VersionFactory  {

        public IoNettyNettyTransportClassesVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: io.netty.netty.transport.classes.epoll (4.1.101.Final)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getEpoll() { return getVersion("io.netty.netty.transport.classes.epoll"); }

    }

    public static class IoSentryVersionAccessors extends VersionFactory  {

        private final IoSentrySentryVersionAccessors vaccForIoSentrySentryVersionAccessors = new IoSentrySentryVersionAccessors(providers, config);
        public IoSentryVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.io.sentry.sentry
         */
        public IoSentrySentryVersionAccessors getSentry() {
            return vaccForIoSentrySentryVersionAccessors;
        }

    }

    public static class IoSentrySentryVersionAccessors extends VersionFactory  implements VersionNotationSupplier {

        public IoSentrySentryVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the version associated to this alias: io.sentry.sentry (7.1.0)
         * If the version is a rich version and that its not expressible as a
         * single version string, then an empty string is returned.
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> asProvider() { return getVersion("io.sentry.sentry"); }

            /**
             * Returns the version associated to this alias: io.sentry.sentry.log4j2 (7.1.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getLog4j2() { return getVersion("io.sentry.sentry.log4j2"); }

    }

    public static class ItVersionAccessors extends VersionFactory  {

        private final ItUnimiVersionAccessors vaccForItUnimiVersionAccessors = new ItUnimiVersionAccessors(providers, config);
        public ItVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.it.unimi
         */
        public ItUnimiVersionAccessors getUnimi() {
            return vaccForItUnimiVersionAccessors;
        }

    }

    public static class ItUnimiVersionAccessors extends VersionFactory  {

        private final ItUnimiDsiVersionAccessors vaccForItUnimiDsiVersionAccessors = new ItUnimiDsiVersionAccessors(providers, config);
        public ItUnimiVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.it.unimi.dsi
         */
        public ItUnimiDsiVersionAccessors getDsi() {
            return vaccForItUnimiDsiVersionAccessors;
        }

    }

    public static class ItUnimiDsiVersionAccessors extends VersionFactory  {

        public ItUnimiDsiVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: it.unimi.dsi.fastutil (8.5.12)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getFastutil() { return getVersion("it.unimi.dsi.fastutil"); }

    }

    public static class NetVersionAccessors extends VersionFactory  {

        private final NetDaporkchopVersionAccessors vaccForNetDaporkchopVersionAccessors = new NetDaporkchopVersionAccessors(providers, config);
        private final NetMinecrellVersionAccessors vaccForNetMinecrellVersionAccessors = new NetMinecrellVersionAccessors(providers, config);
        private final NetSfVersionAccessors vaccForNetSfVersionAccessors = new NetSfVersionAccessors(providers, config);
        public NetVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.net.daporkchop
         */
        public NetDaporkchopVersionAccessors getDaporkchop() {
            return vaccForNetDaporkchopVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.net.minecrell
         */
        public NetMinecrellVersionAccessors getMinecrell() {
            return vaccForNetMinecrellVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.net.sf
         */
        public NetSfVersionAccessors getSf() {
            return vaccForNetSfVersionAccessors;
        }

    }

    public static class NetDaporkchopVersionAccessors extends VersionFactory  {

        private final NetDaporkchopLeveldbVersionAccessors vaccForNetDaporkchopLeveldbVersionAccessors = new NetDaporkchopLeveldbVersionAccessors(providers, config);
        public NetDaporkchopVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.net.daporkchop.leveldb
         */
        public NetDaporkchopLeveldbVersionAccessors getLeveldb() {
            return vaccForNetDaporkchopLeveldbVersionAccessors;
        }

    }

    public static class NetDaporkchopLeveldbVersionAccessors extends VersionFactory  {

        private final NetDaporkchopLeveldbMcpeVersionAccessors vaccForNetDaporkchopLeveldbMcpeVersionAccessors = new NetDaporkchopLeveldbMcpeVersionAccessors(providers, config);
        public NetDaporkchopLeveldbVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.net.daporkchop.leveldb.mcpe
         */
        public NetDaporkchopLeveldbMcpeVersionAccessors getMcpe() {
            return vaccForNetDaporkchopLeveldbMcpeVersionAccessors;
        }

    }

    public static class NetDaporkchopLeveldbMcpeVersionAccessors extends VersionFactory  {

        public NetDaporkchopLeveldbMcpeVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: net.daporkchop.leveldb.mcpe.jni (0.0.10-SNAPSHOT)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getJni() { return getVersion("net.daporkchop.leveldb.mcpe.jni"); }

    }

    public static class NetMinecrellVersionAccessors extends VersionFactory  {

        public NetMinecrellVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: net.minecrell.terminalconsoleappender (1.3.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getTerminalconsoleappender() { return getVersion("net.minecrell.terminalconsoleappender"); }

    }

    public static class NetSfVersionAccessors extends VersionFactory  {

        private final NetSfJoptVersionAccessors vaccForNetSfJoptVersionAccessors = new NetSfJoptVersionAccessors(providers, config);
        public NetSfVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.net.sf.jopt
         */
        public NetSfJoptVersionAccessors getJopt() {
            return vaccForNetSfJoptVersionAccessors;
        }

    }

    public static class NetSfJoptVersionAccessors extends VersionFactory  {

        private final NetSfJoptSimpleVersionAccessors vaccForNetSfJoptSimpleVersionAccessors = new NetSfJoptSimpleVersionAccessors(providers, config);
        public NetSfJoptVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.net.sf.jopt.simple
         */
        public NetSfJoptSimpleVersionAccessors getSimple() {
            return vaccForNetSfJoptSimpleVersionAccessors;
        }

    }

    public static class NetSfJoptSimpleVersionAccessors extends VersionFactory  {

        private final NetSfJoptSimpleJoptVersionAccessors vaccForNetSfJoptSimpleJoptVersionAccessors = new NetSfJoptSimpleJoptVersionAccessors(providers, config);
        public NetSfJoptSimpleVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.net.sf.jopt.simple.jopt
         */
        public NetSfJoptSimpleJoptVersionAccessors getJopt() {
            return vaccForNetSfJoptSimpleJoptVersionAccessors;
        }

    }

    public static class NetSfJoptSimpleJoptVersionAccessors extends VersionFactory  {

        public NetSfJoptSimpleJoptVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: net.sf.jopt.simple.jopt.simple (5.0.4)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getSimple() { return getVersion("net.sf.jopt.simple.jopt.simple"); }

    }

    public static class OrgVersionAccessors extends VersionFactory  {

        private final OrgApacheVersionAccessors vaccForOrgApacheVersionAccessors = new OrgApacheVersionAccessors(providers, config);
        private final OrgBitbucketVersionAccessors vaccForOrgBitbucketVersionAccessors = new OrgBitbucketVersionAccessors(providers, config);
        private final OrgCloudburstmcVersionAccessors vaccForOrgCloudburstmcVersionAccessors = new OrgCloudburstmcVersionAccessors(providers, config);
        private final OrgGraalvmVersionAccessors vaccForOrgGraalvmVersionAccessors = new OrgGraalvmVersionAccessors(providers, config);
        private final OrgJetbrainsVersionAccessors vaccForOrgJetbrainsVersionAccessors = new OrgJetbrainsVersionAccessors(providers, config);
        private final OrgJlineVersionAccessors vaccForOrgJlineVersionAccessors = new OrgJlineVersionAccessors(providers, config);
        private final OrgJunitVersionAccessors vaccForOrgJunitVersionAccessors = new OrgJunitVersionAccessors(providers, config);
        private final OrgLz4VersionAccessors vaccForOrgLz4VersionAccessors = new OrgLz4VersionAccessors(providers, config);
        private final OrgMockitoVersionAccessors vaccForOrgMockitoVersionAccessors = new OrgMockitoVersionAccessors(providers, config);
        private final OrgOpenjdkVersionAccessors vaccForOrgOpenjdkVersionAccessors = new OrgOpenjdkVersionAccessors(providers, config);
        private final OrgOw2VersionAccessors vaccForOrgOw2VersionAccessors = new OrgOw2VersionAccessors(providers, config);
        private final OrgProjectlombokVersionAccessors vaccForOrgProjectlombokVersionAccessors = new OrgProjectlombokVersionAccessors(providers, config);
        private final OrgXerialVersionAccessors vaccForOrgXerialVersionAccessors = new OrgXerialVersionAccessors(providers, config);
        private final OrgYamlVersionAccessors vaccForOrgYamlVersionAccessors = new OrgYamlVersionAccessors(providers, config);
        public OrgVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.apache
         */
        public OrgApacheVersionAccessors getApache() {
            return vaccForOrgApacheVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.bitbucket
         */
        public OrgBitbucketVersionAccessors getBitbucket() {
            return vaccForOrgBitbucketVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.cloudburstmc
         */
        public OrgCloudburstmcVersionAccessors getCloudburstmc() {
            return vaccForOrgCloudburstmcVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.graalvm
         */
        public OrgGraalvmVersionAccessors getGraalvm() {
            return vaccForOrgGraalvmVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.jetbrains
         */
        public OrgJetbrainsVersionAccessors getJetbrains() {
            return vaccForOrgJetbrainsVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.jline
         */
        public OrgJlineVersionAccessors getJline() {
            return vaccForOrgJlineVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.junit
         */
        public OrgJunitVersionAccessors getJunit() {
            return vaccForOrgJunitVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.lz4
         */
        public OrgLz4VersionAccessors getLz4() {
            return vaccForOrgLz4VersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.mockito
         */
        public OrgMockitoVersionAccessors getMockito() {
            return vaccForOrgMockitoVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.openjdk
         */
        public OrgOpenjdkVersionAccessors getOpenjdk() {
            return vaccForOrgOpenjdkVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.ow2
         */
        public OrgOw2VersionAccessors getOw2() {
            return vaccForOrgOw2VersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.projectlombok
         */
        public OrgProjectlombokVersionAccessors getProjectlombok() {
            return vaccForOrgProjectlombokVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.xerial
         */
        public OrgXerialVersionAccessors getXerial() {
            return vaccForOrgXerialVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.yaml
         */
        public OrgYamlVersionAccessors getYaml() {
            return vaccForOrgYamlVersionAccessors;
        }

    }

    public static class OrgApacheVersionAccessors extends VersionFactory  {

        private final OrgApacheLoggingVersionAccessors vaccForOrgApacheLoggingVersionAccessors = new OrgApacheLoggingVersionAccessors(providers, config);
        public OrgApacheVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.apache.logging
         */
        public OrgApacheLoggingVersionAccessors getLogging() {
            return vaccForOrgApacheLoggingVersionAccessors;
        }

    }

    public static class OrgApacheLoggingVersionAccessors extends VersionFactory  {

        private final OrgApacheLoggingLog4jVersionAccessors vaccForOrgApacheLoggingLog4jVersionAccessors = new OrgApacheLoggingLog4jVersionAccessors(providers, config);
        public OrgApacheLoggingVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.apache.logging.log4j
         */
        public OrgApacheLoggingLog4jVersionAccessors getLog4j() {
            return vaccForOrgApacheLoggingLog4jVersionAccessors;
        }

    }

    public static class OrgApacheLoggingLog4jVersionAccessors extends VersionFactory  {

        private final OrgApacheLoggingLog4jLog4jVersionAccessors vaccForOrgApacheLoggingLog4jLog4jVersionAccessors = new OrgApacheLoggingLog4jLog4jVersionAccessors(providers, config);
        public OrgApacheLoggingLog4jVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.apache.logging.log4j.log4j
         */
        public OrgApacheLoggingLog4jLog4jVersionAccessors getLog4j() {
            return vaccForOrgApacheLoggingLog4jLog4jVersionAccessors;
        }

    }

    public static class OrgApacheLoggingLog4jLog4jVersionAccessors extends VersionFactory  {

        private final OrgApacheLoggingLog4jLog4jSlf4j2VersionAccessors vaccForOrgApacheLoggingLog4jLog4jSlf4j2VersionAccessors = new OrgApacheLoggingLog4jLog4jSlf4j2VersionAccessors(providers, config);
        public OrgApacheLoggingLog4jLog4jVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.apache.logging.log4j.log4j.core (2.22.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getCore() { return getVersion("org.apache.logging.log4j.log4j.core"); }

        /**
         * Returns the group of versions at versions.org.apache.logging.log4j.log4j.slf4j2
         */
        public OrgApacheLoggingLog4jLog4jSlf4j2VersionAccessors getSlf4j2() {
            return vaccForOrgApacheLoggingLog4jLog4jSlf4j2VersionAccessors;
        }

    }

    public static class OrgApacheLoggingLog4jLog4jSlf4j2VersionAccessors extends VersionFactory  {

        public OrgApacheLoggingLog4jLog4jSlf4j2VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.apache.logging.log4j.log4j.slf4j2.impl (2.22.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getImpl() { return getVersion("org.apache.logging.log4j.log4j.slf4j2.impl"); }

    }

    public static class OrgBitbucketVersionAccessors extends VersionFactory  {

        private final OrgBitbucketBVersionAccessors vaccForOrgBitbucketBVersionAccessors = new OrgBitbucketBVersionAccessors(providers, config);
        public OrgBitbucketVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.bitbucket.b
         */
        public OrgBitbucketBVersionAccessors getB() {
            return vaccForOrgBitbucketBVersionAccessors;
        }

    }

    public static class OrgBitbucketBVersionAccessors extends VersionFactory  {

        private final OrgBitbucketBCVersionAccessors vaccForOrgBitbucketBCVersionAccessors = new OrgBitbucketBCVersionAccessors(providers, config);
        public OrgBitbucketBVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.bitbucket.b.c
         */
        public OrgBitbucketBCVersionAccessors getC() {
            return vaccForOrgBitbucketBCVersionAccessors;
        }

    }

    public static class OrgBitbucketBCVersionAccessors extends VersionFactory  {

        public OrgBitbucketBCVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.bitbucket.b.c.jose4j (0.9.3)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getJose4j() { return getVersion("org.bitbucket.b.c.jose4j"); }

    }

    public static class OrgCloudburstmcVersionAccessors extends VersionFactory  {

        private final OrgCloudburstmcNettyVersionAccessors vaccForOrgCloudburstmcNettyVersionAccessors = new OrgCloudburstmcNettyVersionAccessors(providers, config);
        public OrgCloudburstmcVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.cloudburstmc.netty
         */
        public OrgCloudburstmcNettyVersionAccessors getNetty() {
            return vaccForOrgCloudburstmcNettyVersionAccessors;
        }

    }

    public static class OrgCloudburstmcNettyVersionAccessors extends VersionFactory  {

        private final OrgCloudburstmcNettyNettyVersionAccessors vaccForOrgCloudburstmcNettyNettyVersionAccessors = new OrgCloudburstmcNettyNettyVersionAccessors(providers, config);
        public OrgCloudburstmcNettyVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.cloudburstmc.netty.netty
         */
        public OrgCloudburstmcNettyNettyVersionAccessors getNetty() {
            return vaccForOrgCloudburstmcNettyNettyVersionAccessors;
        }

    }

    public static class OrgCloudburstmcNettyNettyVersionAccessors extends VersionFactory  {

        private final OrgCloudburstmcNettyNettyTransportVersionAccessors vaccForOrgCloudburstmcNettyNettyTransportVersionAccessors = new OrgCloudburstmcNettyNettyTransportVersionAccessors(providers, config);
        public OrgCloudburstmcNettyNettyVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.cloudburstmc.netty.netty.transport
         */
        public OrgCloudburstmcNettyNettyTransportVersionAccessors getTransport() {
            return vaccForOrgCloudburstmcNettyNettyTransportVersionAccessors;
        }

    }

    public static class OrgCloudburstmcNettyNettyTransportVersionAccessors extends VersionFactory  {

        public OrgCloudburstmcNettyNettyTransportVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.cloudburstmc.netty.netty.transport.raknet (1.0.0.CR1-SNAPSHOT)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getRaknet() { return getVersion("org.cloudburstmc.netty.netty.transport.raknet"); }

    }

    public static class OrgGraalvmVersionAccessors extends VersionFactory  {

        private final OrgGraalvmJsVersionAccessors vaccForOrgGraalvmJsVersionAccessors = new OrgGraalvmJsVersionAccessors(providers, config);
        private final OrgGraalvmSdkVersionAccessors vaccForOrgGraalvmSdkVersionAccessors = new OrgGraalvmSdkVersionAccessors(providers, config);
        private final OrgGraalvmToolsVersionAccessors vaccForOrgGraalvmToolsVersionAccessors = new OrgGraalvmToolsVersionAccessors(providers, config);
        public OrgGraalvmVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.graalvm.js
         */
        public OrgGraalvmJsVersionAccessors getJs() {
            return vaccForOrgGraalvmJsVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.graalvm.sdk
         */
        public OrgGraalvmSdkVersionAccessors getSdk() {
            return vaccForOrgGraalvmSdkVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.graalvm.tools
         */
        public OrgGraalvmToolsVersionAccessors getTools() {
            return vaccForOrgGraalvmToolsVersionAccessors;
        }

    }

    public static class OrgGraalvmJsVersionAccessors extends VersionFactory  {

        private final OrgGraalvmJsJsVersionAccessors vaccForOrgGraalvmJsJsVersionAccessors = new OrgGraalvmJsJsVersionAccessors(providers, config);
        public OrgGraalvmJsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.graalvm.js.js
         */
        public OrgGraalvmJsJsVersionAccessors getJs() {
            return vaccForOrgGraalvmJsJsVersionAccessors;
        }

    }

    public static class OrgGraalvmJsJsVersionAccessors extends VersionFactory  implements VersionNotationSupplier {

        public OrgGraalvmJsJsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the version associated to this alias: org.graalvm.js.js (23.0.0)
         * If the version is a rich version and that its not expressible as a
         * single version string, then an empty string is returned.
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> asProvider() { return getVersion("org.graalvm.js.js"); }

            /**
             * Returns the version associated to this alias: org.graalvm.js.js.scriptengine (23.0.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getScriptengine() { return getVersion("org.graalvm.js.js.scriptengine"); }

    }

    public static class OrgGraalvmSdkVersionAccessors extends VersionFactory  {

        private final OrgGraalvmSdkGraalVersionAccessors vaccForOrgGraalvmSdkGraalVersionAccessors = new OrgGraalvmSdkGraalVersionAccessors(providers, config);
        public OrgGraalvmSdkVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.graalvm.sdk.graal
         */
        public OrgGraalvmSdkGraalVersionAccessors getGraal() {
            return vaccForOrgGraalvmSdkGraalVersionAccessors;
        }

    }

    public static class OrgGraalvmSdkGraalVersionAccessors extends VersionFactory  {

        public OrgGraalvmSdkGraalVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.graalvm.sdk.graal.sdk (23.0.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getSdk() { return getVersion("org.graalvm.sdk.graal.sdk"); }

    }

    public static class OrgGraalvmToolsVersionAccessors extends VersionFactory  {

        public OrgGraalvmToolsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.graalvm.tools.chromeinspector (23.0.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getChromeinspector() { return getVersion("org.graalvm.tools.chromeinspector"); }

            /**
             * Returns the version associated to this alias: org.graalvm.tools.profiler (23.0.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getProfiler() { return getVersion("org.graalvm.tools.profiler"); }

    }

    public static class OrgJetbrainsVersionAccessors extends VersionFactory  {

        public OrgJetbrainsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.jetbrains.annotations (24.1.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getAnnotations() { return getVersion("org.jetbrains.annotations"); }

    }

    public static class OrgJlineVersionAccessors extends VersionFactory  {

        private final OrgJlineJlineVersionAccessors vaccForOrgJlineJlineVersionAccessors = new OrgJlineJlineVersionAccessors(providers, config);
        public OrgJlineVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.jline.jline
         */
        public OrgJlineJlineVersionAccessors getJline() {
            return vaccForOrgJlineJlineVersionAccessors;
        }

    }

    public static class OrgJlineJlineVersionAccessors extends VersionFactory  {

        private final OrgJlineJlineTerminalVersionAccessors vaccForOrgJlineJlineTerminalVersionAccessors = new OrgJlineJlineTerminalVersionAccessors(providers, config);
        public OrgJlineJlineVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.jline.jline.reader (3.25.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getReader() { return getVersion("org.jline.jline.reader"); }

        /**
         * Returns the group of versions at versions.org.jline.jline.terminal
         */
        public OrgJlineJlineTerminalVersionAccessors getTerminal() {
            return vaccForOrgJlineJlineTerminalVersionAccessors;
        }

    }

    public static class OrgJlineJlineTerminalVersionAccessors extends VersionFactory  implements VersionNotationSupplier {

        public OrgJlineJlineTerminalVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the version associated to this alias: org.jline.jline.terminal (3.25.0)
         * If the version is a rich version and that its not expressible as a
         * single version string, then an empty string is returned.
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> asProvider() { return getVersion("org.jline.jline.terminal"); }

            /**
             * Returns the version associated to this alias: org.jline.jline.terminal.jna (3.25.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getJna() { return getVersion("org.jline.jline.terminal.jna"); }

    }

    public static class OrgJunitVersionAccessors extends VersionFactory  {

        private final OrgJunitJupiterVersionAccessors vaccForOrgJunitJupiterVersionAccessors = new OrgJunitJupiterVersionAccessors(providers, config);
        public OrgJunitVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.junit.jupiter
         */
        public OrgJunitJupiterVersionAccessors getJupiter() {
            return vaccForOrgJunitJupiterVersionAccessors;
        }

    }

    public static class OrgJunitJupiterVersionAccessors extends VersionFactory  {

        private final OrgJunitJupiterJunitVersionAccessors vaccForOrgJunitJupiterJunitVersionAccessors = new OrgJunitJupiterJunitVersionAccessors(providers, config);
        public OrgJunitJupiterVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.junit.jupiter.junit
         */
        public OrgJunitJupiterJunitVersionAccessors getJunit() {
            return vaccForOrgJunitJupiterJunitVersionAccessors;
        }

    }

    public static class OrgJunitJupiterJunitVersionAccessors extends VersionFactory  {

        public OrgJunitJupiterJunitVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.junit.jupiter.junit.jupiter (5.10.1)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getJupiter() { return getVersion("org.junit.jupiter.junit.jupiter"); }

    }

    public static class OrgLz4VersionAccessors extends VersionFactory  {

        private final OrgLz4Lz4VersionAccessors vaccForOrgLz4Lz4VersionAccessors = new OrgLz4Lz4VersionAccessors(providers, config);
        public OrgLz4VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.lz4.lz4
         */
        public OrgLz4Lz4VersionAccessors getLz4() {
            return vaccForOrgLz4Lz4VersionAccessors;
        }

    }

    public static class OrgLz4Lz4VersionAccessors extends VersionFactory  {

        public OrgLz4Lz4VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.lz4.lz4.java (1.8.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getJava() { return getVersion("org.lz4.lz4.java"); }

    }

    public static class OrgMockitoVersionAccessors extends VersionFactory  {

        private final OrgMockitoMockitoVersionAccessors vaccForOrgMockitoMockitoVersionAccessors = new OrgMockitoMockitoVersionAccessors(providers, config);
        public OrgMockitoVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.mockito.mockito
         */
        public OrgMockitoMockitoVersionAccessors getMockito() {
            return vaccForOrgMockitoMockitoVersionAccessors;
        }

    }

    public static class OrgMockitoMockitoVersionAccessors extends VersionFactory  {

        private final OrgMockitoMockitoJunitVersionAccessors vaccForOrgMockitoMockitoJunitVersionAccessors = new OrgMockitoMockitoJunitVersionAccessors(providers, config);
        public OrgMockitoMockitoVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.mockito.mockito.junit
         */
        public OrgMockitoMockitoJunitVersionAccessors getJunit() {
            return vaccForOrgMockitoMockitoJunitVersionAccessors;
        }

    }

    public static class OrgMockitoMockitoJunitVersionAccessors extends VersionFactory  {

        public OrgMockitoMockitoJunitVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.mockito.mockito.junit.jupiter (5.8.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getJupiter() { return getVersion("org.mockito.mockito.junit.jupiter"); }

    }

    public static class OrgOpenjdkVersionAccessors extends VersionFactory  {

        private final OrgOpenjdkJmhVersionAccessors vaccForOrgOpenjdkJmhVersionAccessors = new OrgOpenjdkJmhVersionAccessors(providers, config);
        public OrgOpenjdkVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.openjdk.jmh
         */
        public OrgOpenjdkJmhVersionAccessors getJmh() {
            return vaccForOrgOpenjdkJmhVersionAccessors;
        }

    }

    public static class OrgOpenjdkJmhVersionAccessors extends VersionFactory  {

        private final OrgOpenjdkJmhJmhVersionAccessors vaccForOrgOpenjdkJmhJmhVersionAccessors = new OrgOpenjdkJmhJmhVersionAccessors(providers, config);
        public OrgOpenjdkJmhVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.openjdk.jmh.jmh
         */
        public OrgOpenjdkJmhJmhVersionAccessors getJmh() {
            return vaccForOrgOpenjdkJmhJmhVersionAccessors;
        }

    }

    public static class OrgOpenjdkJmhJmhVersionAccessors extends VersionFactory  {

        private final OrgOpenjdkJmhJmhGeneratorVersionAccessors vaccForOrgOpenjdkJmhJmhGeneratorVersionAccessors = new OrgOpenjdkJmhJmhGeneratorVersionAccessors(providers, config);
        public OrgOpenjdkJmhJmhVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.openjdk.jmh.jmh.core (1.37)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getCore() { return getVersion("org.openjdk.jmh.jmh.core"); }

        /**
         * Returns the group of versions at versions.org.openjdk.jmh.jmh.generator
         */
        public OrgOpenjdkJmhJmhGeneratorVersionAccessors getGenerator() {
            return vaccForOrgOpenjdkJmhJmhGeneratorVersionAccessors;
        }

    }

    public static class OrgOpenjdkJmhJmhGeneratorVersionAccessors extends VersionFactory  {

        public OrgOpenjdkJmhJmhGeneratorVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.openjdk.jmh.jmh.generator.annprocess (1.37)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getAnnprocess() { return getVersion("org.openjdk.jmh.jmh.generator.annprocess"); }

    }

    public static class OrgOw2VersionAccessors extends VersionFactory  {

        private final OrgOw2AsmVersionAccessors vaccForOrgOw2AsmVersionAccessors = new OrgOw2AsmVersionAccessors(providers, config);
        public OrgOw2VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.ow2.asm
         */
        public OrgOw2AsmVersionAccessors getAsm() {
            return vaccForOrgOw2AsmVersionAccessors;
        }

    }

    public static class OrgOw2AsmVersionAccessors extends VersionFactory  {

        public OrgOw2AsmVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.ow2.asm.asm (9.6)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getAsm() { return getVersion("org.ow2.asm.asm"); }

    }

    public static class OrgProjectlombokVersionAccessors extends VersionFactory  {

        public OrgProjectlombokVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.projectlombok.lombok (1.18.30)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getLombok() { return getVersion("org.projectlombok.lombok"); }

    }

    public static class OrgXerialVersionAccessors extends VersionFactory  {

        private final OrgXerialSnappyVersionAccessors vaccForOrgXerialSnappyVersionAccessors = new OrgXerialSnappyVersionAccessors(providers, config);
        public OrgXerialVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.xerial.snappy
         */
        public OrgXerialSnappyVersionAccessors getSnappy() {
            return vaccForOrgXerialSnappyVersionAccessors;
        }

    }

    public static class OrgXerialSnappyVersionAccessors extends VersionFactory  {

        private final OrgXerialSnappySnappyVersionAccessors vaccForOrgXerialSnappySnappyVersionAccessors = new OrgXerialSnappySnappyVersionAccessors(providers, config);
        public OrgXerialSnappyVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.xerial.snappy.snappy
         */
        public OrgXerialSnappySnappyVersionAccessors getSnappy() {
            return vaccForOrgXerialSnappySnappyVersionAccessors;
        }

    }

    public static class OrgXerialSnappySnappyVersionAccessors extends VersionFactory  {

        public OrgXerialSnappySnappyVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.xerial.snappy.snappy.java (1.1.10.5)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getJava() { return getVersion("org.xerial.snappy.snappy.java"); }

    }

    public static class OrgYamlVersionAccessors extends VersionFactory  {

        public OrgYamlVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.yaml.snakeyaml (2.2)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getSnakeyaml() { return getVersion("org.yaml.snakeyaml"); }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

    }

    public static class PluginAccessors extends PluginFactory {

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

    }

}
