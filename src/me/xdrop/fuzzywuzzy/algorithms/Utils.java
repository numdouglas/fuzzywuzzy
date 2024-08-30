package me.xdrop.fuzzywuzzy.algorithms;

import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.util.*;

final public class Utils {


    static List<String> tokenize(String in){

        return Arrays.asList(in.split("\\s+"));

    }

    static Set<String> tokenizeSet(String in){

        return new HashSet<>(tokenize(in));

    }

    static String sortAndJoin(List<String> col, String sep){

        Collections.sort(col);

        return join(col, sep);

    }

    //sort tokens2 with reference to tokens1
    static String similaritySortAndJoin(String[] tokens1, String[] tokens2, String sep) {
        for (int i = 0; i < tokens1.length; i++) {
            final int[] distances = new int[tokens2.length];

            for (int j = i; j < tokens2.length; j++) {
                distances[j] = FuzzySearch.ratio(tokens1[i], tokens2[j]);
            }
            //find max match token
            int max = 0, maxpos = 0;

            for (int a = i; a < distances.length; a++) {
                if (distances[a] > max) {
                    maxpos = a;
                    max = distances[a];
                }
            }
            //swap
            final String tmp = tokens2[maxpos];
            tokens2[maxpos] = tokens2[i];
            tokens2[i] = tmp;
        }
        //join
        return join(Arrays.asList(tokens2), sep);
    }

    static String join(List<String> strings, String sep) {
        final StringBuilder buf = new StringBuilder(strings.size() * 16);

        for(int i = 0; i < strings.size(); i++){

            if(i < strings.size()) {
                buf.append(sep);
            }

            buf.append(strings.get(i));

        }

        return buf.toString().trim();
    }

    static String sortAndJoin(Set<String> col, String sep){

        return sortAndJoin(new ArrayList<>(col), sep);

    }

    public static <T extends Comparable<T>> List<T> findTopKHeap(List<T> arr, int k) {
        PriorityQueue<T> pq = new PriorityQueue<T>();

        for (T x : arr) {
            if (pq.size() < k) pq.add(x);
            else if (x.compareTo(pq.peek()) > 0) {
                pq.poll();
                pq.add(x);
            }
        }
        List<T> res = new ArrayList<>();
        for (int i =k; i > 0; i--) {
            T polled = pq.poll();
            if (polled != null) {
                res.add(polled);
            }
        }
        return res;

    }

    static <T extends Comparable<? super T>> T max(T ... elems) {

        if (elems.length == 0) return null;

        T best = elems[0];

        for(T t : elems){
            if (t.compareTo(best) > 0) {
                best = t;
            }
        }

        return best;

    }



}
