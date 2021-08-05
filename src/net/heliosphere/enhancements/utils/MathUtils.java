package net.heliosphere.enhancements.utils;

import java.util.List;

public class MathUtils {

    public static int findIndexInList(List<Integer> list, int element) {
        if (list != null) {
            int index = 0;

            while (index < list.size())
                if (list.get(index) == element)
                    return index;
                else
                    index++;
        }
        return -1;
    }
}