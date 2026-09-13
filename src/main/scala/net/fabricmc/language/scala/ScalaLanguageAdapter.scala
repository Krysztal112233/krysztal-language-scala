package net.fabricmc.language.scala

import net.fabricmc.loader.api.{
    LanguageAdapter,
    LanguageAdapterException,
    ModContainer
}
import net.fabricmc.loader.impl.launch.FabricLauncherBase
import org.slf4j.{Logger, LoggerFactory}

class ScalaLanguageAdapter extends LanguageAdapter {

    private val log: Logger =
        LoggerFactory.getLogger(classOf[ScalaLanguageAdapter])

    override def create[T](
        mod: ModContainer,
        value: String,
        tpe: Class[T]
    ): T = {
        log.debug(s"Constructing for scala mod ${mod.getMetadata.getId}.")

        val module = Seq(value, value + "$").view
            .flatMap(getScalaObject(_, tpe))
            .headOption
        module match {
            case Some(instance) => instance
            case None =>
                throw new LanguageAdapterException(
                  s"Unable to instantiate mod ${mod.getMetadata.getId}."
                )
        }
    }

    private def getScalaObject[T](value: String, tpe: Class[T]): Option[T] = {
        try {
            val clazz = Class.forName(value, true, targetClassLoader)
            val module = clazz.getField("MODULE$").get(null)
            Some(tpe.cast(module))
        } catch {
            case e: ReflectiveOperationException =>
                log.debug(s"Reflecting failed for `$value`: $e")
                None
            case e: ExceptionInInitializerError =>
                log.debug(s"Reflecting failed for `$value`: $e")
                None
        }
    }

    private def targetClassLoader: ClassLoader =
        FabricLauncherBase.getLauncher.getTargetClassLoader
}
