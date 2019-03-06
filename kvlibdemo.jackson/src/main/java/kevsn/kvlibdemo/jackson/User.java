/*
 * 2016年4月18日 
 */
package kevsn.kvlibdemo.jackson;

import java.util.Arrays;

/**
 * @author Kevin
 *
 */
public class User {

	public enum Gender {
		MALE, FEMALE
	};

	public static class Name {

		private String _first, _last;

		public String getFirst() {
			return _first;
		}

		public String getLast() {
			return _last;
		}

		public void setFirst(String s) {
			_first = s;
		}

		public void setLast(String s) {
			_last = s;
		}
	}

	private Gender _gender;
	private Name _name;
	private boolean _isVerified;
	private byte[] _userImage;

	public Name getName() {
		return _name;
	}

	public boolean isVerified() {
		return _isVerified;
	}

	public Gender getGender() {
		return _gender;
	}

	public byte[] getUserImage() {
		return _userImage;
	}

	public void setUserImage(byte[] image) {
		this._userImage = image;
	}

	public void setName(Name n) {
		_name = n;
	}

	public void setVerified(boolean b) {
		_isVerified = b;
	}

	public void setGender(Gender g) {
		_gender = g;
	}

	@Override
	public String toString() {
		return "User [_gender=" + _gender + ", _name=" + _name
				+ ", _isVerified=" + _isVerified + ", _userImage="
				+ Arrays.toString(_userImage) + "]";
	}

}
