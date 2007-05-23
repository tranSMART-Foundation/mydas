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

package uk.ac.ebi.mydas.controller;

import uk.ac.ebi.mydas.exceptions.DataSourceException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * Created Using IntelliJ IDEA.
 * Date: 23-May-2007
 * Time: 14:03:29
 *
 * @author Phil Jones, EMBL-EBI, pjones@ebi.ac.uk
 *
 * The CacheManager is passed to the data source to allow it to control caching
 * in the mydas servlet.  At present, this is limited to a single method allowing
 * the data source to empty the cache.
 */
public class CacheManager {

    /**
     * Reference to the GeneralCacheAdministrator object.
     */
    private GeneralCacheAdministrator cacheAdministrator;

    /**
     * Reference to the data source configuration.
     */
    private DataSourceConfiguration dsnConfig;

    CacheManager (GeneralCacheAdministrator cacheAdministrator, DataSourceConfiguration dsnConfig){
        this.dsnConfig = dsnConfig;
        this.cacheAdministrator = cacheAdministrator;
    }

    public void emptyCache() throws DataSourceException {
        if (dsnConfig.isOK()){
            cacheAdministrator.flushGroup(dsnConfig.getName());
        }
    }
}