package com.xlg.android.protocol;

import com.xlg.android.utils.ByteBuffer;
import com.xlg.android.utils.CRC32;

import java.lang.reflect.Field;

public class Message {	
	// 计算Object长度 
	public static int SizeOfObject(Object obj) {
		int size = 0;
		
		Field fields[] = GetClassStructFiled(obj.getClass());
		for(int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if(null == field) {
				continue;
			}
			
			String type = field.getType().getName();
			// 设置可以访问
			field.setAccessible(true);
			
			try {
				if(type.equalsIgnoreCase("byte")) {
					size += 1;
				} else if (type.equalsIgnoreCase("short")) {
					size += 2;
				} else if(type.equalsIgnoreCase("int")) {
					size += 4;
				} else if(type.equalsIgnoreCase("long")) {
					size += 8;
				} else if(type.equalsIgnoreCase("[B")) {
					byte [] b = (byte [])field.get(obj);
					size += b.length;
				} else if(type.equalsIgnoreCase("com.xlg.android.utils.ByteBuffer")) {
					ByteBuffer buf = (ByteBuffer)field.get(obj);
					size += buf.size();
				}else {
					return -1;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}
		}
		
		return size;
	}
	
	// 编码
	public static boolean EncodePack(ByteBuffer buf, Header head, Object obj, boolean hasCRC) {
		head.setTimer(System.currentTimeMillis());
		// 清除原来的信息
		buf.clear();
		// 构建头部
		// 长度: header + object + crc32
		if(hasCRC) {
			buf.addInt(head.SIZE_HEADER + SizeOfObject(obj) + 4);
		} else {
			buf.addInt(head.SIZE_HEADER + SizeOfObject(obj));
		}
		
		buf.addByte(head.getVersion());
//		buf.addByte(head.getReserve());
		buf.addByte(head.getCmd1());
		buf.addByte(head.getCmd2());
		buf.addByte(head.getCmd3());
		buf.addLong(head.getTimer());

		// 构建结构体		
		Field fields[] = GetClassStructFiled(obj.getClass());
		for(int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if(null == field) {
				continue;
			}

			String type = field.getType().getName();
			// 设置可以访问
			field.setAccessible(true);
			
			try {
				if(type.equalsIgnoreCase("byte")) {
					buf.addByte( field.getByte(obj) );
				} else if (type.equalsIgnoreCase("short")) {
					buf.addShort( field.getShort(obj) );
				} else if(type.equalsIgnoreCase("int")) {
					buf.addInt(field.getInt(obj));
				} else if(type.equalsIgnoreCase("long")) {
					buf.addLong(field.getLong(obj));
				} else if(type.equalsIgnoreCase("[B")) {
					byte [] b = (byte [])field.get(obj);
					buf.addBytes(b, 0, b.length);
				}  else if(type.equalsIgnoreCase("com.xlg.android.utils.ByteBuffer"))  {
					ByteBuffer data = (ByteBuffer)field.get(obj);
					buf.addBytes(data.getData(), 0, data.size());
				} else {
					return false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		if(false == hasCRC) {
			return true;
		}
		
		// 设置CRC
		int crc = CRC32.crc32(buf.getData(), buf.size(), CRC32.CRC_MAGIC);
		buf.addInt(crc);
		
		return true;
	}
	
	// 解码
	public static int DecodeHeader(ByteBuffer buf, Header head) {
		head.setLength( buf.getInt( Header.OFFSET_LENGTH) );
		if(head.getLength() > buf.size()) {
			return 0;
		}
		
		head.setVersion( buf.getByte(Header.OFFSET_VERSION) );
//		head.setReserve( buf.getByte(Header.OFFSET_RESERVE) );
		head.setCmd1( buf.getByte(Header.OFFSET_CMD1) );
		head.setCmd2( buf.getByte(Header.OFFSET_CMD2) );
		head.setCmd3( buf.getByte(Header.OFFSET_CMD3) );
		head.setTimer( buf.getLong(Header.OFFSET_TIMER) );

		return head.getLength();
	}
	
	// 解码数据包
	public static int DecodeObject(ByteBuffer buf, Object obj) {
		int len = buf.getInt(Header.OFFSET_LENGTH);
		if(len > buf.size()) {
			return 0;
		}
		
		// 反序列化对像
		if(false == UnserializeObject(buf, Header.SIZE_HEADER, obj)) {
			return -1;
		}
				
		return len;
	}
	
	public static boolean UnserializeObject(ByteBuffer buf, int index, Object obj) {
		// 构建结构体
		Field fields[] = GetClassStructFiled(obj.getClass());
		for(int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if(null == field) {
				continue;
			}

			String type = field.getType().getName();
			// 设置可以访问
			field.setAccessible(true);
			
			try {
				if(type.equalsIgnoreCase("byte")) {
					field.setByte(obj, buf.getByte(index));
					index += 1;
				} else if (type.equalsIgnoreCase("short")) {
					field.setShort(obj, buf.getShort(index));
					index += 2;
				} else if(type.equalsIgnoreCase("int")) {
					field.setInt(obj, buf.getInt(index));
					index += 4;
				} else if(type.equalsIgnoreCase("long")) {
					field.setLong(obj, buf.getLong(index));
					index += 8;
				} else if(type.equalsIgnoreCase("[B")) {
					byte [] b = (byte [])field.get(obj);
					buf.getBytes(b, index, b.length);
					index += b.length;
				} else if(type.equalsIgnoreCase("com.xlg.android.utils.ByteBuffer"))  {
					ByteBuffer data = (ByteBuffer)field.get(obj);
					int s = buf.size() - index; // - 4; // 出现变代时，不会出现CRC
					if(s < 1) {
						continue;
					}
					
					byte [] d = new byte[s];
					buf.getBytes(d, index, s);
					data.clear();
					data.addBytes(d, 0, s);
					index += s;
				} else {
					return false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	
	// 解析消息体
	public static ByteBuffer DecodeBody(ByteBuffer buf) {
		int len = buf.getInt(Header.OFFSET_LENGTH);
		if(len > buf.size()) {
			return null;
		}
		
		ByteBuffer out = new ByteBuffer();
		out.addBytes(buf.getData(), Header.SIZE_HEADER, len - Header.SIZE_HEADER);
		return out;
	}
	
	// 获取属性列表
	public static Field[] GetClassStructFiled(Class clazz) {
		Field[] fields = clazz.getDeclaredFields();
		Field[] rets = new Field[fields.length];
		
		for(int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			
			if(null == field) {
				continue;
			}
			
			if(false == field.isAnnotationPresent(StructOrder.class)) {
				continue;
			}
			
			StructOrder order = field.getAnnotation(StructOrder.class);
			if(order.value() >= 0 && order.value() < rets.length) {
				rets[order.value()] = field;
			}
		}
		
		return rets;
	}
}
