package com.lgb.accelerate.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ServerReturn implements Parcelable {
	private String message;
	private String result;
	private int status;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ServerReturn [message=" + message + ", result=" + result
				+ ", status=" + status + "]";
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(message);
		dest.writeString(result);
		dest.writeInt(status);
	}
	public static final Creator<ServerReturn> CREATOR = new Creator<ServerReturn>() {
		public ServerReturn createFromParcel(Parcel source) {
			ServerReturn object = new ServerReturn();
			
			object.message = source.readString();
			object.result = source.readString();
			object.status = source.readInt();
			
			return object;
		}
		
		public ServerReturn[] newArray(int size) {
			return new ServerReturn[size];
		}
	};
}
