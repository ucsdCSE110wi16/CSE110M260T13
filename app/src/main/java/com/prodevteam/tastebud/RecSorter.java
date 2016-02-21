package com.prodevteam.tastebud;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ryan Kim on 2/11/2016.
 * Class to help find recommendations
 */
public class RecSorter {
    // first, get info from customer's past orders and it's ingredients
    /* with each ingredient having its own priority, augment that with
    the occurrences of the ingredient. So chicken priority * occurence would
    be that ingredients total priority.
    Then, for each menu item, count the ingredients and add their total priority,
    and organize the menu according to these totals, with higher numbers being
    more recommended.
    This is just an attempt to see how we could do a sort of the ingredients for recs
    TODO: change the structure maybe
     */
    // variable declarations
    private ArrayList<menuAlgData> menuList; // list holding menu data and its priority
    private int[] priorityValues; // this stores priorities of the ingredients
    /* class to hold objects that has both the menudata and the priority */
    public class menuAlgData implements Comparable<menuAlgData>
    {
        private MenuData menuValue;
        private int priority;

        public menuAlgData(MenuData menuValue, int priority) {
            this.menuValue = menuValue;
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }

        public int compareTo(menuAlgData compareData) {
            int comparePriority = ((menuAlgData) compareData).getPriority();
            //ascending order, descending order is reverse ie comparePriority - this.priority
            return this.priority - comparePriority;
        }
    }
    /* function to make a hashmap of ingredients and occurences of that user's orders */
    public HashMap makePriorityHash(String userEmail){
        //TODO: get useremail in string to pass
        String userIng = App.sqlConnection.getUserIngs(userEmail);
        int count;
        // go through string and make a list
        List<String> ingList = Arrays.asList(userIng.split(","));
        HashMap hash = new HashMap(ingList.size());
        // go through list and count the occurrences of the ingredients
        for (int i = 0; i < ingList.size(); i++ ){
            if (hash.containsKey(ingList.get(i))){ // if ing doesn't exist yet, add with value of 1
                hash.put(ingList.get(i),1);
            }
            else { // if ing exists, increment value by one for that inhg key and update
                count = (int)hash.get(ingList.get(i)) + 1;
                hash.put(ingList.get(i),count);
            }
        }
        return hash;
    }
    /* function to sort a list of menu items */
    public void sortRec(String userEmail){
        ArrayList<MenuData> tempList = App.sqlConnection.getMenu();// first get menu list
        HashMap hash = this.makePriorityHash(userEmail);
        // TODO: do the actual sorting
        // Now that I think about it, the menuAlgData class might be completely unnecessary..
    }
}
