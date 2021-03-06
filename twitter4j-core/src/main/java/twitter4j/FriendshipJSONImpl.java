/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package twitter4j;

import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.json.DataObjectFactoryUtil;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

import static twitter4j.internal.util.ParseUtil.getInt;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.9
 */
class FriendshipJSONImpl implements Friendship {
    private static final long serialVersionUID = -7592213959077711096L;
    private final int id;
    private final String name;
    private final String screenName;
    private boolean following = false;
    private boolean followedBy = false;

    /*package*/ FriendshipJSONImpl(HttpResponse res) throws TwitterException {
        this(res.asJSONObject());
        DataObjectFactoryUtil.clearThreadLocalMap();
        DataObjectFactoryUtil.registerJSONObject(this, res.asJSONObject());
    }

    /*package*/ FriendshipJSONImpl(JSONObject json) throws TwitterException {
        super();
        try {
            id = getInt("id", json);
            name = json.getString("name");
            screenName = json.getString("screen_name");
            JSONArray connections = json.getJSONArray("connections");
            for (int i = 0; i < connections.length(); i++) {
                String connection = connections.getString(i);
                if ("following".equals(connection)) {
                    following = true;
                } else if ("followed_by".equals(connection)) {
                    followedBy = true;
                }
            }
        } catch (JSONException jsone) {
            throw new TwitterException(jsone.getMessage() + ":" + json.toString(), jsone);
        }
    }

    /*package*/
    static ResponseList<Friendship> createFriendshipList(HttpResponse res) throws TwitterException {
        try {
            DataObjectFactoryUtil.clearThreadLocalMap();
            JSONArray list = res.asJSONArray();
            int size = list.length();
            ResponseList<Friendship> friendshipList = new ResponseListImpl<Friendship>(size, res);
            for (int i = 0; i < size; i++) {
                JSONObject json = list.getJSONObject(i);
                Friendship friendship = new FriendshipJSONImpl(json);
                DataObjectFactoryUtil.registerJSONObject(friendship, json);
                friendshipList.add(friendship);
            }
            DataObjectFactoryUtil.registerJSONObject(friendshipList, list);
            return friendshipList;
        } catch (JSONException jsone) {
            throw new TwitterException(jsone);
        } catch (TwitterException te) {
            throw te;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public boolean isFollowing() {
        return following;
    }

    public boolean isFollowedBy() {
        return followedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FriendshipJSONImpl that = (FriendshipJSONImpl) o;

        if (followedBy != that.followedBy) return false;
        if (following != that.following) return false;
        if (id != that.id) return false;
        if (!name.equals(that.name)) return false;
        if (!screenName.equals(that.screenName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + screenName.hashCode();
        result = 31 * result + (following ? 1 : 0);
        result = 31 * result + (followedBy ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FriendshipJSONImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", screenName='" + screenName + '\'' +
                ", following=" + following +
                ", followedBy=" + followedBy +
                '}';
    }
}
