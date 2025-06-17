package net.fabricmc.language.scala;

import java.util.Optional;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.fabricmc.loader.api.LanguageAdapter;
import net.fabricmc.loader.api.LanguageAdapterException;
import net.fabricmc.loader.api.ModContainer;

@Slf4j
public class ScalaLanguageAdapter implements LanguageAdapter {

	@Override
	@SneakyThrows
	public <T> T create(ModContainer mod, String value, Class<T> type) {
		log.debug("Constructing for scala mod %s.".formatted(mod.getMetadata().getId()));

		for (String val : Stream.of(value, value + "$").toList()) {
			var container = getScalaObject(mod, val, type);
			if (container.isPresent())
				return container.get();
		}

		throw new LanguageAdapterException("Unable to instantiate mod %s.".formatted(mod));
	}

	@SneakyThrows
	@SuppressWarnings("unchecked")
	private <T> Optional<T> getScalaObject(ModContainer mod, String value, Class<T> type) {
		Object obj = null;
		try {
			var clazz = Class.forName(value, true, ScalaLanguageAdapter.class.getClassLoader());
			obj = clazz.getField("MODULE$").get(null);
		} catch (NoSuchFieldException | ClassNotFoundException e) {
			log.debug("Reflecting failed for `%s`: %s".formatted(value, e));
			return Optional.empty();
		}

		Optional<T> container = Optional.empty();
		try {
			container = Optional.of((T) obj);
		} catch (ClassCastException e) {
			log.warn("Failed to cast object of `%s` into type `%s`: %s".formatted(obj, type, e));
		}
		return container;
	}
}
