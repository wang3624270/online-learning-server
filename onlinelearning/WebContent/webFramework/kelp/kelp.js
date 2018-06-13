(function($) {
	'use strict';
	var escape = /["\\\x00-\x1f\x7f-\x9f]/g, meta = {
		'\b' : '\\b',
		'\t' : '\\t',
		'\n' : '\\n',
		'\f' : '\\f',
		'\r' : '\\r',
		'"' : '\\"',
		'\\' : '\\\\'
	}, hasOwn = Object.prototype.hasOwnProperty;
	$.toJSON = typeof JSON === 'object' && JSON.stringify ? JSON.stringify
			: function(o) {
				if (o === null) {
					return 'null';
				}
				var pairs, k, name, val, type = $.type(o);

				if (type === 'undefined') {
					return undefined;
				}
				if (type === 'number' || type === 'boolean') {
					return String(o);
				}
				if (type === 'string') {
					return $.quoteString(o);
				}
				if (typeof o.toJSON === 'function') {
					return $.toJSON(o.toJSON());
				}
				if (type === 'date') {
					var month = o.getUTCMonth() + 1, day = o.getUTCDate(), year = o
							.getUTCFullYear(), hours = o.getUTCHours(), minutes = o
							.getUTCMinutes(), seconds = o.getUTCSeconds(), milli = o
							.getUTCMilliseconds();

					if (month < 10) {
						month = '0' + month;
					}
					if (day < 10) {
						day = '0' + day;
					}
					if (hours < 10) {
						hours = '0' + hours;
					}
					if (minutes < 10) {
						minutes = '0' + minutes;
					}
					if (seconds < 10) {
						seconds = '0' + seconds;
					}
					if (milli < 100) {
						milli = '0' + milli;
					}
					if (milli < 10) {
						milli = '0' + milli;
					}
					return '"' + year + '-' + month + '-' + day + 'T' + hours
							+ ':' + minutes + ':' + seconds + '.' + milli
							+ 'Z"';
				}
				pairs = [];
				if ($.isArray(o)) {
					for (k = 0; k < o.length; k++) {
						pairs.push($.toJSON(o[k]) || 'null');
					}
					return '[' + pairs.join(',') + ']';
				}
				if (typeof o === 'object') {
					for (k in o) {
						if (hasOwn.call(o, k)) {
							type = typeof k;
							if (type === 'number') {
								name = '"' + k + '"';
							} else if (type === 'string') {
								name = $.quoteString(k);
							} else {
								continue;
							}
							type = typeof o[k];
							if (type !== 'function' && type !== 'undefined') {
								val = $.toJSON(o[k]);
								pairs.push(name + ':' + val);
							}
						}
					}
					return '{' + pairs.join(',') + '}';
				}
			};
	$.evalJSON = typeof JSON === 'object' && JSON.parse ? JSON.parse
			: function(str) {
				return eval('(' + str + ')');
			};
	$.secureEvalJSON = typeof JSON === 'object' && JSON.parse ? JSON.parse
			: function(str) {
				var filtered = str
						.replace(/\\["\\\/bfnrtu]/g, '@')
						.replace(
								/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,
								']').replace(/(?:^|:|,)(?:\s*\[)+/g, '');

				if (/^[\],:{}\s]*$/.test(filtered)) {
					return eval('(' + str + ')');
				}
				throw new SyntaxError(
						'Error parsing JSON, source is not valid.');
			};
	$.quoteString = function(str) {
		if (str.match(escape)) {
			return '"'
					+ str.replace(escape, function(a) {
						var c = meta[a];
						if (typeof c === 'string') {
							return c;
						}
						c = a.charCodeAt();
						return '\\u00' + Math.floor(c / 16).toString(16)
								+ (c % 16).toString(16);
					}) + '"';
		}
		return '"' + str + '"';
	};
}(jQuery));
/*
 * UUID-js: A js library to generate and parse UUIDs, TimeUUIDs and generate
 * TimeUUID based on dates for range selections.
 * 
 * @see http://www.ietf.org/rfc/rfc4122.txt
 */
function UUIDjs() {
};

UUIDjs.maxFromBits = function(bits) {
	return Math.pow(2, bits);
};

UUIDjs.limitUI04 = UUIDjs.maxFromBits(4);
UUIDjs.limitUI06 = UUIDjs.maxFromBits(6);
UUIDjs.limitUI08 = UUIDjs.maxFromBits(8);
UUIDjs.limitUI12 = UUIDjs.maxFromBits(12);
UUIDjs.limitUI14 = UUIDjs.maxFromBits(14);
UUIDjs.limitUI16 = UUIDjs.maxFromBits(16);
UUIDjs.limitUI32 = UUIDjs.maxFromBits(32);
UUIDjs.limitUI40 = UUIDjs.maxFromBits(40);
UUIDjs.limitUI48 = UUIDjs.maxFromBits(48);
function getRandomInt(min, max) {
	return Math.floor(Math.random() * (max - min + 1)) + min;
}
UUIDjs.randomUI04 = function() {
	return getRandomInt(0, UUIDjs.limitUI04 - 1);
};
UUIDjs.randomUI06 = function() {
	return getRandomInt(0, UUIDjs.limitUI06 - 1);
};
UUIDjs.randomUI08 = function() {
	return getRandomInt(0, UUIDjs.limitUI08 - 1);
};
UUIDjs.randomUI12 = function() {
	return getRandomInt(0, UUIDjs.limitUI12 - 1);
};
UUIDjs.randomUI14 = function() {
	return getRandomInt(0, UUIDjs.limitUI14 - 1);
};
UUIDjs.randomUI16 = function() {
	return getRandomInt(0, UUIDjs.limitUI16 - 1);
};
UUIDjs.randomUI32 = function() {
	return getRandomInt(0, UUIDjs.limitUI32 - 1);
};
UUIDjs.randomUI40 = function() {
	return (0 | Math.random() * (1 << 30))
			+ (0 | Math.random() * (1 << 40 - 30)) * (1 << 30);
};
UUIDjs.randomUI48 = function() {
	return (0 | Math.random() * (1 << 30))
			+ (0 | Math.random() * (1 << 48 - 30)) * (1 << 30);
};

UUIDjs.paddedString = function(string, length, z) {
	string = String(string);
	z = (!z) ? '0' : z;
	var i = length - string.length;
	for (; i > 0; i >>>= 1, z += z) {
		if (i & 1) {
			string = z + string;
		}
	}
	return string;
};

UUIDjs.prototype.fromParts = function(timeLow, timeMid, timeHiAndVersion,
		clockSeqHiAndReserved, clockSeqLow, node) {
	this.version = (timeHiAndVersion >> 12) & 0xF;
	this.hex = UUIDjs.paddedString(timeLow.toString(16), 8) + '-'
			+ UUIDjs.paddedString(timeMid.toString(16), 4) + '-'
			+ UUIDjs.paddedString(timeHiAndVersion.toString(16), 4) + '-'
			+ UUIDjs.paddedString(clockSeqHiAndReserved.toString(16), 2)
			+ UUIDjs.paddedString(clockSeqLow.toString(16), 2) + '-'
			+ UUIDjs.paddedString(node.toString(16), 12);
	return this;
};

UUIDjs.prototype.toString = function() {
	return this.hex;
};
UUIDjs.prototype.toURN = function() {
	return 'urn:uuid:' + this.hex;
};

UUIDjs.prototype.toBytes = function() {
	var parts = this.hex.split('-');
	var ints = [];
	var intPos = 0;
	for (var i = 0; i < parts.length; i++) {
		for (var j = 0; j < parts[i].length; j += 2) {
			ints[intPos++] = parseInt(parts[i].substr(j, 2), 16);
		}
	}
	return ints;
};

UUIDjs.prototype.equals = function(uuid) {
	if (!(uuid instanceof UUID)) {
		return false;
	}
	if (this.hex !== uuid.hex) {
		return false;
	}
	return true;
};

UUIDjs.getTimeFieldValues = function(time) {
	var ts = time - Date.UTC(1582, 9, 15);
	var hm = ((ts / 0x100000000) * 10000) & 0xFFFFFFF;
	return {
		low : ((ts & 0xFFFFFFF) * 10000) % 0x100000000,
		mid : hm & 0xFFFF,
		hi : hm >>> 16,
		timestamp : ts
	};
};

UUIDjs._create4 = function() {
	return new UUIDjs().fromParts(UUIDjs.randomUI32(), UUIDjs.randomUI16(),
			0x4000 | UUIDjs.randomUI12(), 0x80 | UUIDjs.randomUI06(), UUIDjs
					.randomUI08(), UUIDjs.randomUI48());
};

UUIDjs._create1 = function() {
	var now = new Date().getTime();
	var sequence = UUIDjs.randomUI14();
	var node = (UUIDjs.randomUI08() | 1) * 0x10000000000 + UUIDjs.randomUI40();
	var tick = UUIDjs.randomUI04();
	var timestamp = 0;
	var timestampRatio = 1 / 4;

	if (now != timestamp) {
		if (now < timestamp) {
			sequence++;
		}
		timestamp = now;
		tick = UUIDjs.randomUI04();
	} else if (Math.random() < timestampRatio && tick < 9984) {
		tick += 1 + UUIDjs.randomUI04();
	} else {
		sequence++;
	}

	var tf = UUIDjs.getTimeFieldValues(timestamp);
	var tl = tf.low + tick;
	var thav = (tf.hi & 0xFFF) | 0x1000;

	sequence &= 0x3FFF;
	var cshar = (sequence >>> 8) | 0x80;
	var csl = sequence & 0xFF;

	return new UUIDjs().fromParts(tl, tf.mid, thav, cshar, csl, node);
};

UUIDjs.create = function(version) {
	version = version || 4;
	return this['_create' + version]();
};

UUIDjs.fromTime = function(time, last) {
	last = (!last) ? false : last;
	var tf = UUIDjs.getTimeFieldValues(time);
	var tl = tf.low;
	var thav = (tf.hi & 0xFFF) | 0x1000; // set version '0001'
	if (last === false) {
		return new UUIDjs().fromParts(tl, tf.mid, thav, 0, 0, 0);
	} else {
		return new UUIDjs().fromParts(tl, tf.mid, thav,
				0x80 | UUIDjs.limitUI06, UUIDjs.limitUI08 - 1,
				UUIDjs.limitUI48 - 1);
	}
};

UUIDjs.firstFromTime = function(time) {
	return UUIDjs.fromTime(time, false);
};
UUIDjs.lastFromTime = function(time) {
	return UUIDjs.fromTime(time, true);
};

UUIDjs.fromURN = function(strId) {
	var r, p = /^(?:urn:uuid:|\{)?([0-9a-f]{8})-([0-9a-f]{4})-([0-9a-f]{4})-([0-9a-f]{2})([0-9a-f]{2})-([0-9a-f]{12})(?:\})?$/i;
	if ((r = p.exec(strId))) {
		return new UUIDjs().fromParts(parseInt(r[1], 16), parseInt(r[2], 16),
				parseInt(r[3], 16), parseInt(r[4], 16), parseInt(r[5], 16),
				parseInt(r[6], 16));
	}
	return null;
};

UUIDjs.fromBytes = function(ints) {
	if (ints.length < 5) {
		return null;
	}
	var str = '';
	var pos = 0;
	var parts = [ 4, 2, 2, 2, 6 ];
	for (var i = 0; i < parts.length; i++) {
		for (var j = 0; j < parts[i]; j++) {
			var octet = ints[pos++].toString(16);
			if (octet.length == 1) {
				octet = '0' + octet;
			}
			str += octet;
		}
		if (parts[i] !== 6) {
			str += '-';
		}
	}
	return UUIDjs.fromURN(str);
};

UUIDjs.fromBinary = function(binary) {
	var ints = [];
	for (var i = 0; i < binary.length; i++) {
		ints[i] = binary.charCodeAt(i);
		if (ints[i] > 255 || ints[i] < 0) {
			throw new Error('Unexpected byte in binary data.');
		}
	}
	return UUIDjs.fromBytes(ints);
};
UUIDjs['new'] = function() {
	return this.create(4);
};
UUIDjs.newTS = function() {
	return this.create(1);
};

// /////////////////////////////////////////////////////////////////////////////
// Seascape Kelp
(function(a) {
	if (a.Kelp) {
		return;
	}
	{
		var ll = {
			viewNum : -1,
			viewChanged : false,
			backbuttonClick : false
		};
		a.Kelp = ll;
		a.Kelp.sessionStorage = sessionStorage;
		var date = new Date();
		a.Kelp.initTime = date.getTime();
		if (window.octopusViewId = undefined) {
			a.Kelp._octopusViewId = window.octopusViewId;
		} else
			a.Kelp._octopusViewId = "c_" + a.Kelp.initTime + "_"
					+ UUIDjs.create(4);

		Kelp.List = function() {
			this.ob = new Array();
			this.add = function(ob) {
				this.ob.push(ob);
			};
			this.size = function() {
				return this.ob.length;
			};
			this.find = function(obj) {
				for (i = 0; i < this.ob.length; i++) {
					if (this.ob[i] == obj)
						return i;
				}
				return -1;
			};
			this.clear = function() {
				for (i = 0; i < this.ob.length; i++) {
					this.ob.pop();
				}
			};
			this.removeByIndex = function(index) {
				index = index - 1;
				for (i = index; i < this.ob.length - 1; i++) {
					this.ob[i] = this.ob[i + 1];
				}
				this.ob.pop();
			};
			this.remove = function(obj) {
				t = -1;
				for (i = 0; i < this.ob.length; i++) {
					if (this.ob[i] == obj) {
						t = i;
						break;
					}
				}
				for (i = t; i < this.ob.length - 1; i++) {
					this.ob[i] = this.ob[i + 1];
				}
				this.ob.pop();
			};
			this.toString = function() {
				str = "";
				for (i = 0; i < this.ob.length; i++) {
					str = str + this.ob[i];
					if (i < this.ob.length - 1)
						str = str + ",";
				}
				return str;
			};
		}
		Kelp.Map = function() {
			this.ob = new Object();
			this.size = 0;
			this.put = function(key, value) {
				this.ob[key] = value;
				this.size++;
			}
			this.get = function(key) {
				return this.ob[key];
			}
			this.toJsonString = function() {
				str = "{";
				for (attr in this.ob) {
					str = str + "\"" + attr + "\":\"" + this.ob[attr] + "\""
							+ ",";
				}
				if (str != "{")
					str = str.substring(0, str.length - 1);
				str = str + "}";

				return str;
			}

		}
	}
})(window);

Kelp.jsonGet = function(_url, jsonObj, successCall) {
	if (jsonObj == undefined)
		jsonObj = new Object();
	jsonObj.octopusViewId = Kelp.getOctopusViewId();
	$.ajax({
		url : _url,
		type : "GET",
		dataType : 'json',
		contentType : "application/json; charset=utf-8",
		mimeType : 'application/json',
		data : jsonObj,
		success : successCall,
		beforeSend : function(xhr) {
			xhr.setRequestHeader("X-Ajax-call", "true");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

		}
	});
}
Kelp.jsonPost = function(_url, jsonObj, successCall) {
	if (jsonObj == undefined)
		jsonObj = new Object();
	jsonObj.octopusViewId = Kelp.getOctopusViewId();
	$.ajax({
		url : _url,
		type : "POST",
		dataType : 'json',
		contentType : "application/json; charset=utf-8",
		mimeType : 'application/json',
		data : JSON.stringify(jsonObj),

		success : successCall,
		beforeSend : function(xhr) {
			xhr.setRequestHeader("X-Ajax-call", "true");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

		}
	});
}
Kelp.createForm = function() {
	return $("<form></form>");

}
Kelp.jsonPostWithForm = function(_url, $form, successCall) {
	if ($form.find("#octopusViewId").html() == undefined) {
		$form
				.append("<input type='hidden' id='octopusViewId' name='octopusViewId' value='"
						+ Kelp.getOctopusViewId() + "' />");
	}
	$.ajax({
		url : _url,
		type : "POST",
		dataType : 'json',
		mimeType : 'application/json',
		data : $form.serialize(),
		success : successCall,
		beforeSend : function(xhr) {
			xhr.setRequestHeader("X-Ajax-call", "true");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

		}
	});

}
Kelp.url = function(str) {
	window.location.href = str;
}
Kelp.isUrl = function(str) {
	var www = /(http):\/\/([^\.\/]+)([\.]?)([^\.\/]+)([\.]?)([^\.\/]+)(\/[\w-\.\/\?\%\&\=]*)?/;
	if (!www.test(str)) {
		return false;
	} else {
		return true;
	}
}
Kelp.isImage = function(a) {
	switch (a) {
	case "jpg":
	case "png":
	case "bmp":
	case "jpeg":
	case "JPG":
	case "PNG":
	case "BMP":
	case "JPEG":
		return true;
		break;
	default:
		return false;
	}
}

Kelp.getRequest = function() {
	var url = location.search;
	var theRequest = new Object();
	if (url.indexOf("?") != -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for (var i = 0; i < strs.length; i++) {
			theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
		}
	}
	return theRequest;
}
Kelp.getOctopusViewId = function() {
	if (window.octopusViewId != undefined)
		return window.octopusViewId;
	else
		return Kelp._octopusViewId;
}

Kelp.setCookie = function(objName, objValue, objHours) {// 添加cookie
	var str = objName + "=" + escape(objValue);
	if (objHours != undefined && objHours > 0) {// 为0时不设定过期时间，浏览器关闭时cookie自动消失
		var date = new Date();
		var ms = objHours * 3600 * 1000;
		date.setTime(date.getTime() + ms);
		str += ";expires=" + date.toGMTString();
	}
	document.cookie = str;
}
Kelp.getCookie = function(objName) {
	var arrStr = document.cookie.split(";");
	for (var i = 0; i < arrStr.length; i++) {
		var temp = arrStr[i].split("=");
		if ($.trim(temp[0]) == $.trim(objName)) {
			return unescape(temp[1]);
		}
	}
}
Kelp.delCookie = function(name) {
	var date = new Date();
	date.setTime(date.getTime() - 10000);
	document.cookie = name + "=a; expires=" + date.toGMTString();
}
Kelp.clearCookie = function() {
	var cookieArry = document.cookie.split(";");
	for (var i = 0; i < cookieArry.length; i++) {
		var temp = cookieArry[i].split("=");
		Kelp.delCookie($.trim(temp[0]));
	}
}
Kelp.allCookie = function() {// 读取所有保存的cookie字符串
	var str = document.cookie;
	if (str == "") {
		str = "没有保存任何cookie";
	}
	alert(str);
}
