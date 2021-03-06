/*
 * Copyright 2007 Philip Jones, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the mydas project, including source code,
 * downloads and documentation, please see:
 *
 * http://code.google.com/p/mydas/
 *
 */

package uk.ac.ebi.mydas.example;

import uk.ac.ebi.mydas.configuration.DataSourceConfiguration;
import uk.ac.ebi.mydas.configuration.PropertyType;
import uk.ac.ebi.mydas.datasource.AlignmentDataSource;
import uk.ac.ebi.mydas.datasource.ReferenceDataSource;
import uk.ac.ebi.mydas.datasource.StructureDataSource;
import uk.ac.ebi.mydas.exceptions.BadReferenceObjectException;
import uk.ac.ebi.mydas.exceptions.DataSourceException;
import uk.ac.ebi.mydas.exceptions.UnimplementedFeatureException;
import uk.ac.ebi.mydas.extendedmodel.DasUnknownFeatureSegment;
import uk.ac.ebi.mydas.model.*;
import uk.ac.ebi.mydas.model.alignment.*;
import uk.ac.ebi.mydas.model.structure.*;

import javax.servlet.ServletContext;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created Using IntelliJ IDEA.
 * Date: 09-May-2007
 * Time: 14:46:59
 *
 * @author Phil Jones, EMBL-EBI, pjones@ebi.ac.uk
 *         <p/>
 *         This test data source is used in conjunction with the WebIntegrationTest test class to test
 *         the running web application.
 */
public class TESTDataSource implements ReferenceDataSource, StructureDataSource, AlignmentDataSource {

    ServletContext svCon;
    Map<String, PropertyType> globalParameters;
    DataSourceConfiguration config;

    /**
     * This method is called by the {@link uk.ac.ebi.mydas.controller.MydasServlet} class at Servlet initialisation.
     * <p/>
     * The AnnotationDataSource is passed the servletContext, a handle to globalParameters in the
     * form of a Map &lt;String, String&gt; and a DataSourceConfiguration object.
     * <p/>
     * The latter two parameters contain all of the pertinent information in the
     * ServerConfig.xml file relating to the server as a whole and specifically to
     * this data source.  This mechanism allows the datasource author to set up
     * required configuration in one place, including AnnotationDataSource specific configuration.
     * <p/>
     * <bold>It is highly desirable for the implementation to test itself in this init method and throw
     * a DataSourceException if it fails, e.g. to attempt to get a Connection to a database
     * and read a record.</bold>
     *
     * @param servletContext   being the ServletContext of the servlet container that the
     *                         Mydas servlet is running in.
     * @param globalParameters being a Map &lt;String, String&gt; of keys and values
     *                         as defined in the ServerConfig.xml file.
     * @param dataSourceConfig containing the pertinent information frmo the ServerConfig.xml
     *                         file for this datasource, including (optionally) a Map of datasource specific configuration.
     * @throws uk.ac.ebi.mydas.exceptions.DataSourceException
     *          should be thrown if there is any
     *          fatal problem with loading this data source.  <bold>It is highly desirable for the implementation to test itself in this init method and throw
     *          a DataSourceException if it fails, e.g. to attempt to get a Connection to a database
     *          and read a record.</bold>
     */
    public void init(ServletContext servletContext, Map<String, PropertyType> globalParameters, DataSourceConfiguration dataSourceConfig) throws DataSourceException {
        this.svCon = servletContext;
        this.globalParameters = globalParameters;
        this.config = dataSourceConfig;
    }

    /**
     * This method is called when the DAS server is shut down and should be used
     * to clean up resources such as database connections as required.
     */
    public void destroy() {
        // In this case, nothing to do.
    }

    /**
     * This method returns a List of DasFeature objects, describing the Features
     * of the segmentReference passed in as argument.
     *
     * @param segmentReference being the reference of the segment requested in the DAS request (not including
     *                         start and stop coordinates)
     *                         <p/>
     *                         If your datasource implements only this interface,
     *                         the MydasServlet will handle restricting the features returned to
     *                         the start / stop coordinates in the request and you will only need to
     *                         implement this method to return Features.  If on the other hand, your data source
     *                         includes massive segments, you may wish to implement the {@link uk.ac.ebi.mydas.datasource.RangeHandlingAnnotationDataSource}
     *                         interface.  It will then be the responsibility of your AnnotationDataSource plugin to
     *                         restrict the features returned for the requested range.
     * @return a List of DasFeature objects.
     * @throws uk.ac.ebi.mydas.exceptions.DataSourceException
     *
     * @throws BadReferenceObjectException
     */
    public DasAnnotatedSegment getFeatures(String segmentReference, Integer maxbins) throws DataSourceException, BadReferenceObjectException {
        try {
            if (segmentReference.equals("one")) {
                Collection<DasFeature> oneFeatures = new ArrayList<DasFeature>(2);
                DasTarget target = new DasTarget("oneTargetId", 20, 30, "oneTargetName");
                oneFeatures.add(new DasFeature(
                        "oneFeatureIdOne",
                        "one Feature Label One",
                        new DasType("oneFeatureTypeIdOne", "oneFeatureCategoryOne", "CV:00001", "one Feature DasType Label One"),
                        new DasMethod("oneFeatureMethodIdOne", "one Feature Method Label One", "ECO:12345"),
                        5,
                        10,
                        123.45,
                        DasFeatureOrientation.ORIENTATION_NOT_APPLICABLE,
                        DasPhase.PHASE_NOT_APPLICABLE,
                        Collections.singleton("This is a note relating to feature one of segment one."),
                        Collections.singletonMap(new URL("http://code.google.com/p/mydas/"), "mydas project home page."),
                        Collections.singleton(target),
                        null,
                        null
                ));
                if (maxbins == null)
                    oneFeatures.add(new DasFeature(
                            "oneFeatureIdTwo",
                            "one Feature Label Two",
                            new DasType("oneFeatureTypeIdTwo", "oneFeatureCategoryTwo", "CV:00002", "one Feature DasType Label Two"),
                            new DasMethod("oneFeatureMethodIdTwo", "one Feature Method Label Two", null),
                            18,
                            25,
                            96.3,
                            DasFeatureOrientation.ORIENTATION_NOT_APPLICABLE,
                            DasPhase.PHASE_NOT_APPLICABLE,
                            Collections.singleton("This is a note relating to feature two of segment one."),
                            Collections.singletonMap(new URL("http://code.google.com/p/mydas/"), "mydas project home page."),

                            null,
                            null,
                            null
                    ));
                return new DasAnnotatedSegment("one", 1, 34, "Up-to-date", "one_label", oneFeatures);
            } else if (segmentReference.equals("two")) {

                Collection<DasFeature> twoFeatures = new ArrayList<DasFeature>(2);
                twoFeatures.add(new DasFeature(
                        "twoFeatureIdOne",
                        "two Feature Label One",
                        new DasType("twoFeatureTypeIdOne", "twoFeatureCategoryOne", "CV:00001", "two Feature DasType Label One"),
                        new DasMethod("twoFeatureMethodIdTwo", "two Featur eMethod Label One", null),
                        9,
                        33,
                        1000.01,
                        DasFeatureOrientation.ORIENTATION_SENSE_STRAND,
                        DasPhase.PHASE_READING_FRAME_0,
                        Collections.singleton("This is a note relating to feature one of segment two."),
                        Collections.singletonMap(new URL("http://code.google.com/p/mydas/"), "mydas project home page."),
                        null,
                        null,
                        null
                ));
                DasAnnotatedSegment segmentTwo = new DasAnnotatedSegment("two", 1, 1000, "Up-to-date", "two_label", twoFeatures);
                DasComponentFeature selfComponent = segmentTwo.getSelfComponentFeature();
                selfComponent.addSubComponent(
                        "Contig:A",
                        1, 200,
                        1, 200,
                        null,
                        new DasType("contig", "component", "SO:0000149", null),
                        "Contig-A",
                        "Contig A",
                        new DasMethod("component", null, null),
                        0.0,
                        DasFeatureOrientation.ORIENTATION_SENSE_STRAND,
                        DasPhase.PHASE_READING_FRAME_0,
                        Collections.singleton("This is a sub-component."),
                        null);
                selfComponent.addSubComponent(
                        "Contig:B",
                        400, 1000,
                        20, 620,
                        null,
                        new DasType("contig", "component", "SO:0000149", null),
                        "Contig-B",
                        "Contig B",
                        new DasMethod("component", null, null),
                        0.00,
                        DasFeatureOrientation.ORIENTATION_SENSE_STRAND,
                        DasPhase.PHASE_READING_FRAME_0,
                        Collections.singleton("This is a sub-component with different coordinate system."),
                        null
                );
                DasComponentFeature c = selfComponent.addSubComponent(
                        "Contig:C",
                        200, 400,
                        80, 280,
                        null,
                        new DasType("contig", "component", "SO:0000149", null),
                        "Contig-C",
                        "Contig C",
                        new DasMethod("component", null, null),
                        0.00,
                        DasFeatureOrientation.ORIENTATION_SENSE_STRAND,
                        DasPhase.PHASE_READING_FRAME_0,
                        null,
                        null
                );
                c.addSubComponent(
                        "Contig:C.1",
                        200, 400,
                        80, 280,
                        null,
                        new DasType("contig", "component", "SO:0000149", null),
                        "Contig-C.1",
                        "Contig C.1",
                        new DasMethod("component", null, null),
                        0.00,
                        DasFeatureOrientation.ORIENTATION_SENSE_STRAND,
                        DasPhase.PHASE_READING_FRAME_0,
                        null,
                        null
                );

                // And a super component
                selfComponent.addSuperComponent("ParentChromosome",
                        1, 10000,
                        1, 1000,
                        null,
                        new DasType("Chromosome", "supercomponent", "SO:0000340", null),
                        "Parent",
                        null,
                        new DasMethod("supercomponent", null, null),
                        0.00,
                        DasFeatureOrientation.ORIENTATION_SENSE_STRAND,
                        DasPhase.PHASE_READING_FRAME_0,
                        null,
                        null
                );
                //System.out.println("SELFS:["+selfComponent.toString()+"]");

                return segmentTwo;
            } else throw new BadReferenceObjectException(segmentReference, "Not found");
        } catch (MalformedURLException e) {
            throw new DataSourceException("Tried to create an invalid URL for a LINK element.", e);
        }
    }

    /**
     * This method is used to implement the DAS types command.  (See <a href="http://biodas.org/documents/spec.html#types">
     * DAS 1.53 Specification : types command</a>.  This method should return a Collection containing <b>all</b> the
     * types described by the data source (one DasType object for each type ID).
     * <p/>
     * For some data sources it may be desirable to populate this Collection from a configuration file or to
     *
     * @return a Collection of DasType objects - one for each type id described by the data source.
     * @throws uk.ac.ebi.mydas.exceptions.DataSourceException
     *          should be thrown if there is any
     *          fatal problem with loading this data source.  <bold>It is highly desirable
     *          for the implementation to test itself in this init method and throw
     *          a DataSourceException if it fails, e.g. to attempt to get a Connection to a database
     *          and read a record.</bold>
     */
    public Collection<DasType> getTypes() throws DataSourceException {
        Collection<DasType> types = new ArrayList<DasType>(5);
        types.add(new DasType("oneFeatureTypeIdOne", "oneFeatureCategoryOne", "CV:00001", null));
        types.add(new DasType("oneFeatureTypeIdTwo", "oneFeatureCategoryTwo", "CV:00002", null));
        types.add(new DasType("twoFeatureTypeIdOne", "twoFeatureCategoryOne", "CV:00003", null));
        types.add(new DasType("Chromosome", null, null, null));
        types.add(new DasType("Contig", null, null, null));
        return types;
    }

    /**
     * <b>For some Datasources, especially ones with many entry points, this method may be hard or impossible
     * to implement.  If this is the case, you should just throw an {@link uk.ac.ebi.mydas.exceptions.UnimplementedFeatureException} as your
     * implementation of this method, so that a suitable error HTTP header
     * (X-DAS-Status: 501 Unimplemented feature) is returned to the DAS client as
     * described in the DAS 1.53 protocol.</b><br/><br/>
     * <p/>
     * This method is used by the features command when no segments are included, but feature_id and / or
     * group_id filters have been included, to meet the following specification:<br/><br/>
     * <p/>
     * "<b>feature_id</b> (zero or more; new in 1.5)<br/>
     * Instead of, or in addition to, <b>segment</b> arguments, you may provide one or more <b>feature_id</b>
     * arguments, whose values are the identifiers of particular features.  If the server supports this operation,
     * it will translate the feature ID into the segment(s) that strictly enclose them and return the result in
     * the <i>features</i> response.  It is possible for the server to return multiple segments if the requested
     * feature is present in multiple locations.
     * <b>group_id</b> (zero or more; new in 1.5)<br/>
     * The <b>group_id</b> argument, is similar to <b>feature_id</b>, but retrieves segments that contain
     * the indicated feature group."  (Direct quote from the DAS 1.53 specification, available from
     * <a href="http://biodas.org/documents/spec.html">http://biodas.org/documents/spec.html</a>.)
     * <p/>
     * Note that if segments are included in the request, this method is not used, so feature_id and group_id
     * filters accompanying a list of segments will work, even if your implementation of this method throws an
     * {@link uk.ac.ebi.mydas.exceptions.UnimplementedFeatureException}.
     *
     * @param featureIdCollection a Collection&lt;String&gt; of feature_id values included in the features command / request.
     *                            May be a <code>java.util.Collections.EMPTY_LIST</code> but will <b>not</b> be null.
     * @return A Collection of {@link uk.ac.ebi.mydas.model.DasAnnotatedSegment} objects. These describe the segments that is annotated, limited
     *         to the information required for the /DASGFF/GFF/SEGMENT element.  Each References a Collection of
     *         DasFeature objects.   Note that this is a basic Collection - this gives you complete control over the details
     *         of the Collection type - so you can create your own comparators etc.
     * @throws uk.ac.ebi.mydas.exceptions.DataSourceException
     *          should be thrown if there is any
     *          fatal problem with loading this data source.  <bold>It is highly desirable for the implementation to test itself in this init method and throw
     *          a DataSourceException if it fails, e.g. to attempt to get a Connection to a database
     *          and read a record.</bold>
     * @throws uk.ac.ebi.mydas.exceptions.UnimplementedFeatureException
     *          Throw this if you cannot
     *          provide a working implementation of this method.
     */
    public Collection<DasAnnotatedSegment> getFeatures(Collection<String> featureIdCollection, Integer maxbins) throws UnimplementedFeatureException, DataSourceException {
        Collection<DasAnnotatedSegment> segments = new ArrayList<DasAnnotatedSegment>(featureIdCollection.size());
        for (String featureId : featureIdCollection) {
            Collection<DasFeature> oneFeatures = new ArrayList<DasFeature>();
            DasTarget target = new DasTarget("oneTargetId", 20, 30, "oneTargetName");
            if (featureId.equals("oneFeatureIdOne")) {
                try {
                    oneFeatures.add(new DasFeature(
                            "oneFeatureIdOne",
                            "one Feature Label One",
                            new DasType("oneFeatureTypeIdOne", "oneFeatureCategoryOne", "CV:00001", "one Feature DasType Label One"),
                            new DasMethod("oneFeatureMethodIdOne", "one Feature Method Label One", "ECO:12345"),
                            5,
                            10,
                            123.45,
                            DasFeatureOrientation.ORIENTATION_NOT_APPLICABLE,
                            DasPhase.PHASE_NOT_APPLICABLE,
                            Collections.singleton("This is a note relating to feature one of segment one."),
                            Collections.singletonMap(new URL("http://code.google.com/p/mydas/"), "mydas project home page."),
                            Collections.singleton(target),
                            null,
                            null
                    ));
                } catch (MalformedURLException e) {
                }
                segments.add(new DasAnnotatedSegment("one", 1, 34, "Up-to-date", "one_label", oneFeatures));
            }
            if (featureId.equals("oneFeatureIdTwo")) {
                try {
                    oneFeatures.add(new DasFeature(
                            "oneFeatureIdTwo",
                            "one Feature Label Two",
                            new DasType("oneFeatureTypeIdTwo", "oneFeatureCategoryTwo", "CV:00002", "one Feature DasType Label Two"),
                            new DasMethod("oneFeatureMethodIdTwo", "one Feature Method Label Two", null),
                            18,
                            25,
                            96.3,
                            DasFeatureOrientation.ORIENTATION_NOT_APPLICABLE,
                            DasPhase.PHASE_NOT_APPLICABLE,
                            Collections.singleton("This is a note relating to feature two of segment one."),
                            Collections.singletonMap(new URL("http://code.google.com/p/mydas/"), "mydas project home page."),

                            null,
                            null,
                            null
                    ));
                } catch (MalformedURLException e) {
                }
                segments.add(new DasAnnotatedSegment("one", 1, 34, "Up-to-date", "one_label", oneFeatures));
            }
            if (featureId.equals("twoFeatureIdOne")) {
                try {
                    oneFeatures.add(new DasFeature(
                            "twoFeatureIdOne",
                            "two Feature Label One",
                            new DasType("twoFeatureTypeIdOne", "twoFeatureCategoryOne", "CV:00001", "two Feature DasType Label One"),
                            new DasMethod("twoFeatureMethodIdTwo", "two Featur eMethod Label One", null),
                            9,
                            33,
                            1000.01,
                            DasFeatureOrientation.ORIENTATION_SENSE_STRAND,
                            DasPhase.PHASE_READING_FRAME_0,
                            Collections.singleton("This is a note relating to feature one of segment two."),
                            Collections.singletonMap(new URL("http://code.google.com/p/mydas/"), "mydas project home page."),
                            null,
                            null,
                            null
                    ));
                } catch (MalformedURLException e) {
                }
                segments.add(new DasAnnotatedSegment("two", 1, 1000, "Up-to-date", "two_label", oneFeatures));

            }
            if (oneFeatures.isEmpty()) {
                segments.add(new DasUnknownFeatureSegment(featureId));
            }
        }
        return segments;
    }

    /**
     * This method allows the DAS server to report a total count for a particular type
     * for all annotations across the entire data source.  If it is not possible to retrieve this value from your dsn, you
     * should return <code>null</code>.
     *
     * @param type containing the information needed to retrieve the type count
     *             (type id and optionally the method id and category id.  Note that the last two may
     *             be null, which needs to be taken into account by the implementation.)
     * @return The total count <i>across the entire data source</i> (not
     *         just for one segment) for the specified type.  If it is not possible to determine
     *         this count, this method should return <code>null</code>.
     * @throws uk.ac.ebi.mydas.exceptions.DataSourceException
     *          should be thrown if there is any
     *          fatal problem with loading this data source.  <bold>It is highly desirable for the
     *          implementation to test itself in this init method and throw
     *          a DataSourceException if it fails, e.g. to attempt to get a Connection to a database
     *          and read a record.</bold>
     */
    public Integer getTotalCountForType(DasType type) throws DataSourceException {
        if (type.getId() == "Contig")
            return new Integer(3);
        if (type.getId() == "Chromosome")
            return new Integer(1);
        if (type.getId() == "oneFeatureTypeIdTwo")
            return new Integer(1);
        if (type.getId() == "twoFeatureTypeIdOne")
            return new Integer(1);
        if (type.getId() == "oneFeatureTypeIdOne")
            return new Integer(1);

        return null;
    }

    /**
     * This method returns a URL, based upon a request built as part of the DAS 'link' command.
     * The nature of this URL is entirely up to the data source implementor.
     * <p/>
     * The mydas servlet will redirect to the URL provided.  This command is intended for use in an internet browser,
     * so the URL returned should be a valid internet address.  The page can return content of any MIME type and
     * is intended to be 'human readable' rather than material for consumption by a DAS client.
     * <p/>
     * The link command takes two mandatory
     * arguments:
     * <ul>
     * <li>
     * a 'field' parameter which is limited to one of five valid values.  This method is guaranteed
     * to be called with the 'field' parameter set to one of these values (any other request will be handled as
     * an error by the mydas DAS server servlet.)  The 'field' parameter will be one of the five static String constants
     * that are members of the AnnotationDataSource interface.
     * </li>
     * <li>
     * an 'id' field.  Again, this will be validated by the mydas servlet to ensure that it
     * is a non-null, non-zero length String.
     * </li>
     * See <a href="http://biodas.org/documents/spec.html#feature_linking">DAS 1.53 Specification: Linking to a Feature</a>
     * for details.
     * <p/>
     * If your data source does not implement this method, an UnimplementedFeatureException should be thrown.
     *
     * @param field one of 'feature', 'type', 'method', 'category' or 'target' as documented in the DAS 1.53
     *              specification
     * @param id    being the ID of the indicated annotation field
     * @return a valid URL.
     * @throws uk.ac.ebi.mydas.exceptions.UnimplementedFeatureException
     *          in the event that the DAS data source
     *          does not implement the link command
     * @throws uk.ac.ebi.mydas.exceptions.DataSourceException
     *          should be thrown if there is any
     *          fatal problem with loading this data source.  <bold>It is highly desirable for the implementation
     *          to test itself in this init method and throw
     *          a DataSourceException if it fails, e.g. to attempt to get a Connection to a database
     *          and read a record.</bold>
     */
    public URL getLinkURL(String field, String id) throws UnimplementedFeatureException, DataSourceException {
        throw new UnimplementedFeatureException("Link URL is not implemented");
    }

    /**
     * Returns a Collection of {@link DasEntryPoint} objects to implement the entry_point command.
     *
     * @return a Collection of {@link DasEntryPoint} objects
     * @throws uk.ac.ebi.mydas.exceptions.DataSourceException
     *          to encapsulate any exceptions thrown by the datasource
     *          and allow the {@link uk.ac.ebi.mydas.controller.MydasServlet} to return a decent error header to the client.
     */
    public Collection<DasEntryPoint> getEntryPoints(Integer start, Integer stop) throws DataSourceException {
        List<DasEntryPoint> entryPoints = new ArrayList<DasEntryPoint>();
        if (start == 1 && stop == this.getTotalEntryPoints()) { //Test with no rows
            entryPoints.add(new DasEntryPoint("one", 1, 34, "Protein", "1", null, "Its a protein!", false));
            entryPoints.add(new DasEntryPoint("two", 1, 48, "DNA", null, DasEntryPointOrientation.POSITIVE_ORIENTATION, "Its a chromosome!", true));
        } else if (start == 2 && stop == 2) { //test with rows=2-2
            entryPoints.add(new DasEntryPoint("two", 1, 48, "DNA", null, DasEntryPointOrientation.POSITIVE_ORIENTATION, "Its a chromosome!", true));
        }
        return entryPoints;
    }

    public int getTotalEntryPoints() throws DataSourceException {
        return 2;
    }

    /**
     * Extends the {@link uk.ac.ebi.mydas.datasource.ReferenceDataSource} inteface to allow the creation of an Annotation
     * data source.  The only significant difference is that a Reference data source can also
     * serve the sequenceString of the requested segment.
     *
     * @param segmentReference being the name of the sequenceString being requested.
     * @return a {@link DasSequence} object, holding the sequenceString and start / end coordinates of the sequenceString.
     *         to inform the {@link uk.ac.ebi.mydas.controller.MydasServlet} that the
     *         segment requested is not available from this DataSource.
     * @throws uk.ac.ebi.mydas.exceptions.DataSourceException
     *          to encapsulate any exceptions thrown by the datasource
     *          and allow the MydasServlet to return a decent error header to the client.
     */
    public DasSequence getSequence(String segmentReference) throws DataSourceException, BadReferenceObjectException {
        if (segmentReference.equals("one")) {
            return new DasSequence("one", "FFDYASTDFYASDFAUFDYFVSHCVYTDASVCYT", 1, "Up-to-date", null);
        } else if (segmentReference.equals("two")) {
            return new DasSequence("two", "cgatcatcagctacgtacgatcagtccgtacgatcgatcagcatcaca", 1, "Up-to-date", "label_two");
        } else throw new BadReferenceObjectException(segmentReference, "Not found");
    }


    /**
     * Returns the value to be returned from the entry_points command, specifically
     * the /DASEP/ENTRY_POINTS/@version attribute.
     * <p/>
     * This is a <b>mandatory</b> value so you must ensure that this method does not
     * return null or an empty String. (The {@link uk.ac.ebi.mydas.controller.MydasServlet} will return an error to the
     * client if you do).
     *
     * @return a non-null, non-zero length String, being the version number of the
     *         entry points / datasource.
     * @throws uk.ac.ebi.mydas.exceptions.DataSourceException
     *          to encapsulate any exceptions thrown by the datasource
     *          and allow the {@link uk.ac.ebi.mydas.controller.MydasServlet} to return a decent error header to the client.
     */
    public String getEntryPointVersion() throws DataSourceException {
        return "Version 1.1";
    }

    public DasStructure getStructure(String structureId,
                                     Collection<String> chainIdCollection,
                                     Collection<String> modelIdCollection)
            throws DataSourceException {

        DasObject obj = new DasObject("2ii9", null, "20-MAR-07", null, "PDB", "20070116", "PDBresnum,Protein Structure");
        Collection<DasAtom> atoms = new ArrayList<DasAtom>(6);
        atoms.add(new DasAtom(44.18, 5.327, 31.168, "N", "1", null, null, null));
        atoms.add(new DasAtom(43.672, 5.068, 29.781, "CA", "2", null, null, null));
        atoms.add(new DasAtom(42.728, 6.217, 29.365, "C", "3", null, null, null));
        atoms.add(new DasAtom(42.328, 7.024, 30.23, "O", "4", null, null, null));
        atoms.add(new DasAtom(42.965, 3.707, 29.74, "CB", "5", null, null, null));
        atoms.add(new DasAtom(42.754, 3.284, 28.41, "OG", "6", null, null, null));
        Group group = new Group("SER", GroupType.TYPE_AMINO, "1", null, atoms);
        DasChain chain = new DasChain("A", null, null, Collections.singleton(group));
        DasStructure structure = new DasStructure(obj, null, Collections.singleton(chain), null);
        return structure;
    }

    public DasAlignment getAlignment(String alignmentId,
                                     Collection<String> subjects, String subjectcoordsys,
                                     Integer rowStart, Integer rowEnd, Integer colStart, Integer colEnd)
            throws DataSourceException {

        Collection<AlignObject> alignObjects = new ArrayList<AlignObject>(2);
        alignObjects.add(new AlignObject("PF03344", "93d32837b9b401f3bac6ef3d21f9193c", "A2V6V1", AlignType.TYPE_PROTEIN, "Pfam", "24.0", "UniProt", null, "TPSSVEMDISSSRKQSEEPFTTVLENGAGMVSSTSFNGGVSPHNWGDSGPPCKKSRKEKKQTGSGPLGNSYVERQRSVHEK"));
        alignObjects.add(new AlignObject("PF03344", "28a2bfce7453560927cd37b695907c5e", "Q4R3H3", AlignType.TYPE_PROTEIN, "Pfam", "24.0", "UniProt", null, "MAQDAFRDVGIRLQERRHLDLIYNFGCHLTDDYRPGIDPALSDPVLARRLRENRSLAMSRLDEVISKYAMLQDKSEEGERKKRRARLQGTSSHSEDTPASLDSGEGPSGMASQGCPSASKAETDDEEDEESDEEEEEEEDEEEEEEEEEEATDSEEEEDLEQMQEGQEDDEEEEEEEEAAGKDGDGSPMSSPQISTEKNLEPGKQISRSSGEQQNKVSPLLLSEEPLAPSSIDAESNGEQPEELTLEEESPVSQLFELEIEALPLDTPSSVEMDISSSRKQSEEPFTTVLENGAGMVSSTSFNGGVSPHNWGDSGPPCKKSRKEKKQTGSGPLGNSYVERQRSVHEKNGKKICTLPSPPSPLASLAPVADSSTRVDSPSHGLVTSSLCIPSPAQLSQTPQSQPPRPSTYKTSVATQCDPEEIIVLSDSD"));
        Map<String, Double> scores = null;
        Collection<Segment> segments = new ArrayList<Segment>(2);
        segments.add(new Segment("A2V6V1", 1, 81, null, "78I4M11DI23D6I8DI38D2IDI3DI7DI8D4I28DI16D3I4D3I5DIDI7D9I11D2I4D67I10D12I11D3ID3I45D28I26DI13D25I115DI5DI4DIDI4DI7D6I15D2I12D3I3DI5D4I8D22I19D2I5D5I6D7IDIDI5D3I7D37I5D2I4DI7D2I2D2IDI10D5I5D7I4D3I6DI8DI17D7I3M2I9MI14MI29M10I13M6I3M6DI20D6I12D9I11D3I20D6M167I"));
        segments.add(new Segment("Q4R3H3", 1, 429, null, "82I11DI23D6I8DI38D2IDI3DI7DI8D4I28DI16D3I4D3I5DIDI7D9I11D2I4D67I10D12I11D3ID3I45D28I26DI13D25I42D73MI5MI4MIMI4MID6M6ID14MI13M3I3MI5M4IM2D5M22I19M2I5M5I6M7IMIMI5M3I16M18I15M2I12M2I2M2IMI4M3D3M5I5M7I4M3I6MI8MI20MI6M2I9MI14MI29M10I49M6I55M173I"));
        Collection<Block> blocks = Collections.singleton(new Block(null, "1", segments));
        Collection<Geo3D> geo3ds = Collections.singleton(new Geo3D("interiorId", new AlignmentVector(1.1, 2.2, 3.3), new AlignmentMatrix(1.1, 1.2, 1.3, 2.1, 2.2, 2.3, 3.1, 3.2, 3.3)));
        Alignment alignment = new Alignment("Pfam Full Alignment", "PF03344", null, 1, 84, alignObjects, scores, blocks, geo3ds);
        return new DasAlignment(Collections.singleton(alignment));
    }

    @Override
    public DasAnnotatedSegment getFeatures(String segmentId, Integer maxbins,
                                           Range rows) throws BadReferenceObjectException,
            DataSourceException, UnimplementedFeatureException {
        throw new UnimplementedFeatureException("The rows-for-feature capability has not been implemented");
    }

    @Override
    public Collection<DasAnnotatedSegment> getFeatures(
            Collection<String> featureIdCollection, Integer maxbins, Range rows)
            throws UnimplementedFeatureException, DataSourceException {
        throw new UnimplementedFeatureException("The rows-for-feature capability has not been implemented");
    }

}
