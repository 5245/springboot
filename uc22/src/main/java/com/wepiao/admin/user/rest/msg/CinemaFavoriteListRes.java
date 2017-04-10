package com.wepiao.admin.user.rest.msg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CinemaFavoriteListRes extends BaseRes {
    private List<CinemaFavorite> CinemaFavorites;

    public List<CinemaFavorite> get_CinemaFavorites() {
        return CinemaFavorites;
    }

    public void set_CinemaFavorites(List<CinemaFavorite> openIDList) {
        CinemaFavorites = openIDList;
    }

    public CinemaFavoriteListRes() {
        super();
        if (CinemaFavorites == null) {
            CinemaFavorites = new ArrayList<CinemaFavorite>();
        }
    }

    public void addCinemaFavorite(String cinemaID) {
        if (CinemaFavorites == null) {
            CinemaFavorites = new ArrayList<CinemaFavorite>();
        }
        CinemaFavorites.add(new CinemaFavorite(cinemaID));
    }

    public void addCinemaFavoriteArray(String[] cinemaIDs) {
        if (CinemaFavorites == null) {
            CinemaFavorites = new ArrayList<CinemaFavorite>();
        }
        if (cinemaIDs != null) {
            for (String ele : cinemaIDs) {
                CinemaFavorites.add(new CinemaFavorite(ele));
            }
        }
    }

    public void addCinemaFavoriteHashSet(HashSet<String> cinemaIDs) {
        if (CinemaFavorites == null) {
            CinemaFavorites = new ArrayList<CinemaFavorite>();
        }
        if (cinemaIDs != null) {
            for (String ele : cinemaIDs) {
                CinemaFavorites.add(new CinemaFavorite(ele));
            }
        }
    }

    class CinemaFavorite {
        private String CinemaID;

        public String get_CinemaID() {
            return CinemaID;
        }

        public void set_CinemaID(String cinemaID) {
            CinemaID = cinemaID;
        }

        public CinemaFavorite(String cinemaID) {
            super();
            CinemaID = cinemaID;
        }
    }
}
