/*
 * GT-Mconf: Multiconference system for interoperable web and mobile
 * http://www.inf.ufrgs.br/prav/gtmconf
 * PRAV Labs - UFRGS
 * 
 * This file is part of Mconf-Mobile.
 *
 * Mconf-Mobile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Mconf-Mobile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Mconf-Mobile.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mconf.bbb.chat;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.flazr.amf.Amf0Object;

public class ChatMessage {
	
	/*
	 * newMessage = event.message 
	 * 		+ "|" + attributes.username 
	 * 		+ "|" + event.color 
	 * 		+ "|" + event.time 
	 * 		+ "|" + event.language 
	 * 		+ "|" + attributes.userid;
	 */
	private String message;
	private String username;
	private String color;
	private String time;
	private String language;
	private String userId;
	// Additional 0.81 attributes
	private String fromLang;
	// Additional 0.91 attributes
	private String chatType;
	private String toUsername;
	private String toUserID;
	private String fromUsername;
	private String fromUserID;
	private String fromColor;
	private String fromTimezoneOffset;
	private String fromTime;

	public ChatMessage() {
		color = "0";
		time = new SimpleDateFormat("HH:mm").format(System.currentTimeMillis());
		language = "en";
		// Initializing 0.81 attributes
		fromLang = "en";
		// Initializing 0.91 attributes
		fromColor = color;
		fromTimezoneOffset = getTimeZoneOffset();
		fromTime = new Long(System.currentTimeMillis()).toString();
	}
	
	public ChatMessage(Object object) {
		if (object instanceof String)
			decode((String) object);
		else if (object instanceof Amf0Object)
			decode((Amf0Object) object);
	}
	
	// {message=hi, username=rapo, color=0, time=11:52, language=it, userid=4865, classname=org.bigbluebutton.conference.service.chat.ChatObject}]
	private void decode(Amf0Object obj) {
		message = (String) obj.get("message");
		username = (String) obj.get("username");
		color = (String) obj.get("color");
		time = (String) obj.get("time");
		language = (String) obj.get("language");
		userId = (String) obj.get("userid");

		ChatModule.MESSAGE_ENCODING = ChatModule.MESSAGE_ENCODING_TYPED_OBJECT;
	}
	
	private void decode(String s) throws NumberFormatException {
		List<String> param = Arrays.asList(s.split("\\|"));
		message = param.get(0);
		username = param.get(1);

		Collections.reverse(param);
		
		userId = param.get(0);
		language = param.get(1);
		time = param.get(2);
		color = param.get(3);
		
		ChatModule.MESSAGE_ENCODING = ChatModule.MESSAGE_ENCODING_STRING;
	}
	
	public Object encode() {
		switch (ChatModule.MESSAGE_ENCODING) {
			case ChatModule.MESSAGE_ENCODING_STRING: return encodeString();
			case ChatModule.MESSAGE_ENCODING_TYPED_OBJECT: return encodeTypedObject();
			default: return encodeTypedObject();
		}
	}
	
	public Object encode0Dot9() {
		return encodeMappedObject();
}

	public Object encode0Dot81() {
		return encodeMappedObject0Dot81();
}

	private Map<String,Object> encodeMappedObject() {
		Map<String,Object> obj = new HashMap<>();
		obj.put("message", message);
		obj.put("chatType", chatType);
		obj.put("toUsername", toUsername);
		obj.put("toUserID", toUserID);
		obj.put("fromUsername", fromUsername);
		obj.put("fromUserID", fromUserID);
		obj.put("fromColor", fromColor);
		obj.put("fromTimezoneOffset", fromTimezoneOffset);
		obj.put("fromTime", fromTime);
		return obj;
	}
	private Map<String,Object> encodeMappedObject0Dot81() {
		Map<String,Object> obj = new HashMap<>();
		obj.put("message", message);
		obj.put("chatType", chatType);
		obj.put("toUsername", toUsername);
		obj.put("toUserID", toUserID);
		obj.put("fromUsername", fromUsername);
		obj.put("fromUserID", fromUserID);
		obj.put("fromColor", fromColor);
		obj.put("fromTimezoneOffset", fromTimezoneOffset);
		obj.put("fromTime", fromTime);
		obj.put("fromLang", fromLang);
		return obj;
	}

private Object encodeTypedObject() {
	Amf0Object obj = new Amf0Object();
	obj.put("message", message);
	obj.put("username", username);
	obj.put("color", color);
	obj.put("time", time);
	obj.put("language", language);
	obj.put("userid", userId);
	obj.put("classname", "org.bigbluebutton.conference.service.chat.ChatObject");
	return obj;
}
	
	private Object encodeString() {
		StringBuilder sb = new StringBuilder();
		sb.append(message)
			.append("|")
			.append(username)
			.append("|")
			.append(color)
			.append("|")
			.append(time)
			.append("|")
			.append(language)
			.append("|")
			.append(userId);
		return sb.toString();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userid) {
		this.userId = userid;
	}

	public String getChatType() {
		return chatType;
	}

	public void setChatType(String chatType) {
		this.chatType = chatType;
	}

	public String getToUsername() {
		return toUsername;
	}

	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
	}

	public String getToUserID() {
		return toUserID;
	}

	public void setToUserID(String toUserID) {
		this.toUserID = toUserID;
	}

	public String getFromColor() {
		return fromColor;
	}

	public void setFromColor(String fromColor) {
		this.fromColor = fromColor;
	}

	public String getFromTimezoneOffset() {
		return fromTimezoneOffset;
	}

	public void setFromTimezoneOffset(String fromTimezoneOffset) {
		this.fromTimezoneOffset = fromTimezoneOffset;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getFromUsername() {
		return fromUsername;
	}

	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}

	public String getFromUserID() {
		return fromUserID;
	}

	public void setFromUserID(String fromUserID) {
		this.fromUserID = fromUserID;
	}

	@Override
	public String toString() {
		return "ChatMessage [color=" + color + ", language=" + language
				+ ", message=" + message + ", time=" + time + ", userid="
				+ userId + ", username=" + username + "]";
	}
	
	public String getTimeZoneOffset()
	{
	    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
	    String   timeZone = new SimpleDateFormat("Z").format(calendar.getTime());
	    return timeZone.substring(0, 3) + ""+ timeZone.substring(3, 5);
	}
	public String getTimeZoneOffset0Dot81()
	{
	    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
	    String   timeZone = new SimpleDateFormat("Z").format(calendar.getTime());
	    return timeZone.substring(1, 3) + ""+ timeZone.substring(3, 5);
	}
	
}
