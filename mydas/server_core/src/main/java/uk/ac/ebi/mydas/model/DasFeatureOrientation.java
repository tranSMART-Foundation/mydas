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

package uk.ac.ebi.mydas.model;

/**
 * Created using IntelliJ IDEA.
 * Date: 26-May-2007
 * Time: 15:35:39
 *
 * @author Phil Jones, EMBL-EBI, pjones@ebi.ac.uk
 *
 * This enumeration provides the three possibilities for the mandatory
 * /DASGFF/GFF/SEGMENT/FEATURE/ORIENTATION element.
 */
public enum DasFeatureOrientation {

    ORIENTATION_NOT_APPLICABLE("0"),
    ORIENTATION_SENSE_STRAND("+"),
    ORIENTATION_ANTISENSE_STRAND("-");

    private String representation;

    private DasFeatureOrientation(String representation){
        this.representation = representation;
    }

    public String toString(){
        return representation;
    }
}
