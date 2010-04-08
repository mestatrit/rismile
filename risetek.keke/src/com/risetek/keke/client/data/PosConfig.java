/*
 * Copyright (C) 2001 Global Retail Technology, LLC
 * <http://www.globalretailtech.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package com.risetek.keke.client.data;

import java.util.Vector;


/**
 * The base entity for the pos configuration. Manages
 * menus, keys and pos parameters.
 *
 * @author  Quentin Olson
 */
public class PosConfig extends BusinessObject {

    public static final int TYPE = 5;

    private static String table;
    private static String[] columns;
    private static int[] col_types;

    static {

        table = "pos_config";

        columns = new String[3];

        columns[0] = "config_id";
        columns[1] = "config_no";
        columns[2] = "name";

        col_types = new int[3];

        col_types[0] = DBRecord.INT;
        col_types[1] = DBRecord.INT;
        col_types[2] = DBRecord.STRING;
    }

    private int configid;
    private int configno;
    private String configname;

    public PosConfig() {
    }

    public int configID() {
        return configid;
    }

    public int configNo() {
        return configno;
    }

    public String configName() {
        return configname;
    }

    public void setConfigID(int value) {
        configid = value;
    }

    public void setConfigNo(int value) {
        configno = value;
    }

    public void setConfigName(String value) {
        configname = value;
    }



    // fetch specs

    public static String getByID(int id) {

        StringBuffer s = new StringBuffer("select * from ");

        s.append(table);
        s.append(" where ");
        s.append(columns[0]);
        s.append(" = ");
        s.append(Integer.toString(id));

        return new String(s.toString());
    }

    public static String getByNo(int no) {

        StringBuffer s = new StringBuffer("select * from ");

        s.append(table);
        s.append(" where ");
        s.append(columns[1]);
        s.append(" = ");
        s.append(Integer.toString(no));

        return new String(s.toString());
    }

    public static String getAll() {

        StringBuffer s = new StringBuffer("select * from ");
        s.append(table);
        return new String(s.toString());
    }

    public Vector<Object> parents() {
        return null;
    }

    public DBRecord copy() {
        PosConfig b = new PosConfig();
        return b;
    }

    public boolean save() {
        return true;
    }

    public boolean update() {
        return true;
    }

    public int boID() {
        return configID();
    }

    public int boType() {
        return TYPE;
    }

    public String toString() {
        return toXML();
    }
/*
    private HashMap<String,Dialog> dialogs;

    public HashMap<String,Dialog> dialogs() {
        return dialogs;
    }

    public void setDialogs(HashMap<String,Dialog> value) {
        dialogs = value;
    }
*/
    public void relations() {

    }

    /** Abstract implementation of toXML () */
    public String toXML() {
        return super.toXML(table, columnObjects(), columns, col_types);
    }

    public Vector<Object> columnObjects() {

        Vector<Object> objs = new Vector<Object>();

        objs.addElement(new Integer(configID()));
        objs.addElement(new Integer(configNo()));
        objs.addElement(new String(configName() == null ? "" : configName()));

        return objs;
    }
}


