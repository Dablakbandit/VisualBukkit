package com.gmail.visualbukkit.extensions;

import com.gmail.visualbukkit.VisualBukkit;
import com.gmail.visualbukkit.blocks.BlockRegistry;
import com.gmail.visualbukkit.blocks.CodeBlock;
import com.gmail.visualbukkit.gui.NotificationManager;
import javafx.application.Platform;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.FileChooser;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExtensionManager {

    private static Path extensionsFolder = VisualBukkit.getDataFolder().resolve("Extensions");
    private static Set<String> extensions = new HashSet<>();

    public static void init() {
        try {
            if (Files.notExists(extensionsFolder)) {
                Files.createDirectory(extensionsFolder);
            }
            try (DirectoryStream<Path> pathStream = Files.newDirectoryStream(extensionsFolder)) {
                for (Path path : pathStream) {
                    if (path.toString().endsWith(".jar")) {
                        try (URLClassLoader classLoader = new URLClassLoader(new URL[]{path.toUri().toURL()}, ExtensionManager.class.getClassLoader())) {
                            Reflections reflections = new Reflections(classLoader);
                            for (Class<? extends VisualBukkitExtension> clazz : reflections.getSubTypesOf(VisualBukkitExtension.class)) {
                                clazz.getConstructor().newInstance().init();
                            }
                            reflections.getSubTypesOf(CodeBlock.class).forEach(BlockRegistry::registerBlock);
                            extensions.add(path.getFileName().toString());
                        } catch (Exception e) {
                            NotificationManager.displayException("Failed to load extension", e);
                        }
                    }
                }
            }
        } catch (IOException e) {
            NotificationManager.displayException("Failed to load extensions", e);
            Platform.exit();
        }
    }

    public static void promptInstall() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jar", "*.jar"));
        List<File> jarFiles = fileChooser.showOpenMultipleDialog(VisualBukkit.getInstance().getPrimaryStage());
        if (jarFiles != null && jarFiles.size() > 0) {
            for (File jarFile : jarFiles) {
                try {
                    Files.copy(jarFile.toPath(), extensionsFolder.resolve(jarFile.getName()), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    NotificationManager.displayException("Failed to install extension", e);
                }
            }
            NotificationManager.displayMessage("Installed Extensions", "Restart Visual Bukkit to complete installation");
        }
    }

    public static void promptUninstall() {
        ChoiceDialog<String> uninstallDialog = new ChoiceDialog<>();
        uninstallDialog.getItems().addAll(extensions);
        uninstallDialog.setTitle("Uninstall Extension");
        uninstallDialog.setContentText("Extension:");
        uninstallDialog.setHeaderText(null);
        uninstallDialog.setGraphic(null);
        uninstallDialog.showAndWait().ifPresent(extension -> {
            try {
                Files.deleteIfExists(extensionsFolder.resolve(extension));
                NotificationManager.displayMessage("Uninstalled extension", "Restart Visual Bukkit to complete uninstallation");
            } catch (IOException e) {
                NotificationManager.displayException("Failed to uninstall extension", e);
            }
        });
    }
}