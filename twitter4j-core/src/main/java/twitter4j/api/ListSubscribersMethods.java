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

package twitter4j.api;

import twitter4j.PagableResponseList;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.UserList;

/**
 * @author Joern Huxhorn - jhuxhorn at googlemail.com
 */
public interface ListSubscribersMethods {
    /**
     * Returns the subscribers of the specified list.
     * <br>This method calls http://api.twitter.com/1/:user/:list_id/subscribers.json
     *
     * @param listOwnerScreenName The screen name of the list owner
     * @param listId              The id of the list
     * @param cursor              Breaks the results into pages. A single page contains 20 lists. Provide a value of -1 to begin paging. Provide values as returned to in the response body's next_cursor and previous_cursor attributes to page back and forth in the list.
     * @return the members of the specified list.
     * @throws twitter4j.TwitterException when Twitter service or network is unavailable
     * @see <a href="http://dev.twitter.com/doc/get/:user/:list_id/subscribers">GET :user/:list_id/subscribers | dev.twitter.com</a>
     * @since Twitter4J 2.1.0
     */
    PagableResponseList<User> getUserListSubscribers(String listOwnerScreenName, int listId, long cursor)
            throws TwitterException;

    /**
     * Make the authenticated user follow the specified list.
     * <br>This method calls http://api.twitter.com/1/:listOwner/:listId/subscribers.json
     *
     * @param listOwnerScreenName The screen name of the list owner
     * @param listId              The id of the list.
     * @return the updated list
     * @throws TwitterException when Twitter service or network is unavailable
     * @see <a href="http://dev.twitter.com/doc/post/:user/:list_id/subscribers">POST :user/:list_id/subscribers | dev.twitter.com</a>
     * @since Twitter4J 2.1.0
     */
    UserList subscribeUserList(String listOwnerScreenName, int listId)
            throws TwitterException;

    /**
     * Unsubscribes the authenticated user form the specified list.
     * <br>This method calls http://api.twitter.com/1/:listOwner/:listId/subscribers.json
     *
     * @param listOwnerScreenName The screen name of the list owner
     * @param listId              The id of the list.
     * @return the updated list
     * @throws TwitterException when Twitter service or network is unavailable
     * @see <a href="http://dev.twitter.com/doc/delete/:user/:list_id/subscribers">DELETE :user/:list_id/subscribers | dev.twitter.com</a>
     * @since Twitter4J 2.1.0
     */
    UserList unsubscribeUserList(String listOwnerScreenName, int listId)
            throws TwitterException;

    /**
     * Check if the specified user is a subscriber of the specified list.
     * <br>This method calls http://api.twitter.com/1/:listOwner/:listId/subscribers/:userId.json
     *
     * @param listOwnerScreenName The screen name of the list owner
     * @param listId              The id of the list.
     * @param userId              The id of the user who you want to know is a member or not of the specified list.
     * @return the updated list
     * @throws TwitterException when Twitter service or network is unavailable
     *                          , or the user is not a member of the specified list(TwitterException.getStatusCode() returns 404 in that case.)
     * @see <a href="http://dev.twitter.com/doc/get/:user/:list_id/subscribers/:id">GET :user/:list_id/subscribers/:id | dev.twitter.com</a>
     * @since Twitter4J 2.1.0
     */
    User checkUserListSubscription(String listOwnerScreenName, int listId, long userId)
            throws TwitterException;
}
