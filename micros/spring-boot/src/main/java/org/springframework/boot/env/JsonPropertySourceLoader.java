package org.springframework.boot.env;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

/**
 * Strategy to load '.json' files into a {@link PropertySource}.
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月25日 上午11:15:21
 *
 */
public class JsonPropertySourceLoader implements PropertySourceLoader {
	@Override
	public String[] getFileExtensions() {
		return new String[] { "json" };
	}

	@Override
	public PropertySource<?> load(final String name, final Resource resource, final String profile) throws IOException {
		final Map<String, Object> result = mapPropertySource(resource);
		return new MapPropertySource(name, result);
	}

	private Map<String, Object> mapPropertySource(final Resource resource) throws IOException {
		if (resource == null) {
			return null;
		}
		final Map<String, Object> result = new HashMap<String, Object>();
		final JsonParser parser = JsonParserFactory.getJsonParser();
		final Map<String, Object> map = parser.parseMap(readFile(resource));
		nestMap("", result, map);
		return result;
	}

	@SuppressWarnings("unchecked")
	private void nestMap(String prefix, final Map<String, Object> result, final Map<String, Object> map) {
		if (prefix.length() > 0) {
			prefix += ".";
		}
		for (final Map.Entry<String, Object> entrySet : map.entrySet()) {
			if (entrySet.getValue() instanceof Map) {
				nestMap(prefix + entrySet.getKey(), result, (Map<String, Object>) entrySet.getValue());
			} else {
				result.put(prefix + entrySet.getKey().toString(), entrySet.getValue());
			}
		}
	}

	private String readFile(final Resource resource) throws IOException {
		final InputStream inputStream = resource.getInputStream();
		final List<Byte> byteList = new LinkedList<Byte>();
		final byte[] readByte = new byte[1024];
		int length;
		while ((length = inputStream.read(readByte)) > 0) {
			for (int i = 0; i < length; i++) {
				byteList.add(readByte[i]);
			}
		}
		final byte[] allBytes = new byte[byteList.size()];
		int index = 0;
		for (final Byte soloByte : byteList) {
			allBytes[index] = soloByte;
			index += 1;
		}
		return new String(allBytes);
	}
}
