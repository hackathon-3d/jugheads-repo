package com.hackathon.jugs.TweetHunt;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ubuntu on 8/24/13.
 */
public class TweetData implements Serializable {
    public long sendTimeUTC;
    public String message = "";
    public String oauthKey = "";
}
