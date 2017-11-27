package com.example.android.flavoradi;

/**
 * Created by Lyreks on 10/23/2017.
 */

public class Restaurant {
    private int _id;
    private String _restname;
    private int _total_tweets;
    private int _num_updated;

    public Restaurant(){
    }

    public Restaurant(String restname, int total_tweets, int num_updated) {
        this._restname = restname;
        this._total_tweets = total_tweets;
        this._num_updated = num_updated;
    }

    public void set_restname(String new_restname) {
        this._restname = new_restname;
    }

    public void set_total_tweets(int new_total_tweets) {
        this._total_tweets = new_total_tweets;
    }

    public void set_num_updated(int new_num_updated) {
        this._num_updated = new_num_updated;
    }

    public String get_restname()  {
        return _restname;
    }

    public int get_total_tweets() {
        return _total_tweets;
    }

    public int get_num_updated() {
        return _num_updated;
    }

    public void update_total_tweets(int new_tweets) {
        this._total_tweets += new_tweets;
    }

    public void increment_num_updated(){
        this._num_updated++;
    }

}
