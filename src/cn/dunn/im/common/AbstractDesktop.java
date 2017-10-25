package cn.dunn.im.common;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Light.Point;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import cn.dunn.im.container.ResourceContainer;

public abstract class AbstractDesktop {
	protected Point point = new Point();

	protected abstract Scene getScene();

	protected abstract Stage getStage();

	protected abstract Parent getRoot();

	protected abstract int minIndex();

	protected abstract void before();

	protected abstract int closeIndex();

	protected abstract void handle();

	public void show() {
		getStage().setScene(getScene());
		getRoot().setOnMousePressed(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				MouseEvent e = (MouseEvent) event;

				point.setX(getStage().getX() - e.getScreenX());
				point.setY(getStage().getY() - e.getScreenY());
			}
		});

		getRoot().setOnMousePressed(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				MouseEvent e = (MouseEvent) event;
				point.setX(getStage().getX() - e.getScreenX());
				point.setY(getStage().getY() - e.getScreenY());
			}

		});
		getRoot().setOnMouseReleased(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (getStage().getY() < 0) {
					getStage().setY(0);
				}
			}

		});
		getRoot().setOnMouseDragged(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				MouseEvent e = (MouseEvent) event;
				if (getStage().isFullScreen()) {
					return;
				}

				final double x = (e.getScreenX() + point.getX());
				final double y = (e.getScreenY() + point.getY());

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						getStage().setX(x);
						getStage().setY(y);
						if (getStage().getY() < 0) {
							getStage().setY(0);
						}

					}
				});
			}

		});

		final ImageView minImage = (ImageView) getRoot().getChildrenUnmodifiable().get(minIndex());
		minImage.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				getStage().setIconified(true);
			}
		});

		minImage.setOnMouseEntered(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				minImage.setImage(ResourceContainer.getMin_1());
			}
		});

		minImage.setOnMouseExited(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				minImage.setImage(ResourceContainer.getMin());
			}
		});

		final ImageView closeImage = (ImageView) getRoot().getChildrenUnmodifiable().get(closeIndex());

		closeImage.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				before();
				getStage().close();
			}

		});
		closeImage.setOnMouseEntered(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				closeImage.setImage(ResourceContainer.getClose_1());
			}
		});

		closeImage.setOnMouseExited(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				closeImage.setImage(ResourceContainer.getClose());
			}
		});
		


		handle();
		getStage().show();
	}
}
