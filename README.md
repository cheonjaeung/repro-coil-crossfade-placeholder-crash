# Reproduction of coil crashing with crossfade and vector placeholder painter

A reproduction repository for a crash on [Coil](https://github.com/coil-kt/coil)'s `AsyncImage` placeholder in a complex case.

`AsyncImage` crashes under the following conditions:

- Load image which has an extreme aspect ratio (for instance, 1:14). 
- Crossfade is enabled.
- Content scale is `ContentScale.Crop`.
- Vector placeholder painter is provided through `AsyncImage`'s parameter.
- `AsyncImage` enters composition for the first time.

```kotlin
AsyncImage(
    modifier = Modifier.fillMaxWidth().aspectRatio(1f),
    model = ImageRequest.Builder(LocalPlatformContext.current)
        .data("image")
        .crossfade(true)
        .build(),
    contentDescription = null,
    contentScale = ContentScale.Crop,
    placeholder = painterResource(R.drawable.ic_placeholder),
)
```

The crash is reproduced on the following environment:

- Coil Version: 3.6.1
- Device: Galaxy S23, Android 16

## How to Reproduce

1. Launch the app.
2. Enable all switches.
3. Click "Show Image" button.

## Stacktrace

```
FATAL EXCEPTION: main
Process: com.cheonjaeung.repro.coil, PID: 25732
java.lang.RuntimeException: Canvas: trying to draw too large(197233936bytes) bitmap.
	at android.graphics.RecordingCanvas.throwIfCannotDraw(RecordingCanvas.java:268)
	at android.graphics.BaseRecordingCanvas.drawBitmap(BaseRecordingCanvas.java:99)
	at androidx.compose.ui.graphics.AndroidCanvas.drawImageRect-HPBpro0(AndroidCanvas.android.kt:224)
	at androidx.compose.ui.graphics.drawscope.CanvasDrawScope.drawImage-AZ2fEMs(CanvasDrawScope.kt:262)
	at androidx.compose.ui.node.LayoutNodeDrawScope.drawImage-AZ2fEMs(LayoutNodeDrawScope.kt:24)
	at androidx.compose.ui.graphics.drawscope.DrawScope.drawImage-AZ2fEMs$default(DrawScope.kt:555)
	at androidx.compose.ui.graphics.vector.DrawCache.drawInto(DrawCache.kt:96)
	at androidx.compose.ui.graphics.vector.VectorComponent.draw(Vector.kt:181)
	at androidx.compose.ui.graphics.vector.VectorPainter.onDraw(VectorPainter.kt:238)
	at androidx.compose.ui.graphics.painter.Painter.draw-x_KDEd0(Painter.kt:192)
	at coil3.compose.CrossfadePainter.drawPainter(CrossfadePainter.kt:151)
	at coil3.compose.CrossfadePainter.onDraw(CrossfadePainter.kt:90)
	at androidx.compose.ui.graphics.painter.Painter.draw-x_KDEd0(Painter.kt:192)
	at coil3.compose.AsyncImagePainter.onDraw(AsyncImagePainter.kt:206)
	at androidx.compose.ui.graphics.painter.Painter.draw-x_KDEd0(Painter.kt:192)
	at coil3.compose.internal.AbstractContentPainterNode.draw(ContentPainterModifier.kt:451)
	at androidx.compose.ui.node.LayoutNodeDrawScope.drawDirect-eZhPAX0$ui(LayoutNodeDrawScope.kt:132)
	at androidx.compose.ui.node.LayoutNodeDrawScope.draw-eZhPAX0$ui(LayoutNodeDrawScope.kt:119)
	at androidx.compose.ui.node.NodeCoordinator.drawContainedDrawModifiers(NodeCoordinator.kt:515)
	at androidx.compose.ui.node.NodeCoordinator.draw(NodeCoordinator.kt:504)
	at androidx.compose.ui.node.LayoutModifierNodeCoordinator.performDraw(LayoutModifierNodeCoordinator.kt:275)
	at androidx.compose.ui.node.NodeCoordinator.drawContainedDrawModifiers(NodeCoordinator.kt:512)
	at androidx.compose.ui.node.NodeCoordinator.draw(NodeCoordinator.kt:504)
	at androidx.compose.ui.node.LayoutModifierNodeCoordinator.performDraw(LayoutModifierNodeCoordinator.kt:275)
	at androidx.compose.ui.node.NodeCoordinator.drawContainedDrawModifiers(NodeCoordinator.kt:512)
	at androidx.compose.ui.node.NodeCoordinator.draw(NodeCoordinator.kt:504)
	at androidx.compose.ui.node.LayoutModifierNodeCoordinator.performDraw(LayoutModifierNodeCoordinator.kt:275)
	at androidx.compose.ui.node.NodeCoordinator.drawContainedDrawModifiers(NodeCoordinator.kt:512)
	at androidx.compose.ui.node.NodeCoordinator.draw(NodeCoordinator.kt:504)
	at androidx.compose.ui.node.LayoutNode.draw$ui(LayoutNode.kt:1041)
	at androidx.compose.ui.node.InnerNodeCoordinator.performDraw(InnerNodeCoordinator.kt:179)
	at androidx.compose.ui.node.NodeCoordinator.drawContainedDrawModifiers(NodeCoordinator.kt:512)
	at androidx.compose.ui.node.NodeCoordinator.draw(NodeCoordinator.kt:504)
	at androidx.compose.ui.node.LayoutModifierNodeCoordinator.performDraw(LayoutModifierNodeCoordinator.kt:275)
	at androidx.compose.ui.node.NodeCoordinator.drawContainedDrawModifiers(NodeCoordinator.kt:512)
	at androidx.compose.ui.node.NodeCoordinator.draw(NodeCoordinator.kt:504)
	at androidx.compose.ui.node.LayoutModifierNodeCoordinator.performDraw(LayoutModifierNodeCoordinator.kt:275)
	at androidx.compose.ui.node.NodeCoordinator.drawContainedDrawModifiers(NodeCoordinator.kt:512)
	at androidx.compose.ui.node.NodeCoordinator.draw(NodeCoordinator.kt:504)
	at androidx.compose.ui.node.LayoutNode.draw$ui(LayoutNode.kt:1041)
	at androidx.compose.ui.node.InnerNodeCoordinator.performDraw(InnerNodeCoordinator.kt:179)
	at androidx.compose.ui.node.NodeCoordinator.drawContainedDrawModifiers(NodeCoordinator.kt:512)
	at androidx.compose.ui.node.NodeCoordinator.draw(NodeCoordinator.kt:504)
	at androidx.compose.ui.node.LayoutNode.draw$ui(LayoutNode.kt:1041)
	at androidx.compose.ui.node.InnerNodeCoordinator.performDraw(InnerNodeCoordinator.kt:179)
	at androidx.compose.ui.node.NodeCoordinator.drawContainedDrawModifiers(NodeCoordinator.kt:512)
	at androidx.compose.ui.node.NodeCoordinator.draw(NodeCoordinator.kt:504)
	at androidx.compose.ui.node.LayoutNode.draw$ui(LayoutNode.kt:1041)
	at androidx.compose.ui.node.InnerNodeCoordinator.performDraw(InnerNodeCoordinator.kt:179)
	at androidx.compose.ui.node.NodeCoordinator.drawContainedDrawModifiers(NodeCoordinator.kt:512)
	at androidx.compose.ui.node.NodeCoordinator._get_drawBlock_$lambda$0(NodeCoordinator.kt:538)
	at androidx.compose.ui.node.NodeCoordinator$$ExternalSyntheticLambda0.invoke(D8$$SyntheticClass:0)
	at androidx.compose.runtime.snapshots.SnapshotStateObserver.observeReads(SnapshotStateObserver.kt:758)
	at androidx.compose.ui.node.NodeCoordinator._get_drawBlock_$lambda$1(NodeCoordinator.kt:2587)
	at androidx.compose.ui.node.NodeCoordinator$$ExternalSyntheticLambda1.invoke(D8$$SyntheticClass:0)
	at androidx.compose.ui.platform.GraphicsLayerOwnerLayer.recordLambda$lambda$0(GraphicsLayerOwnerLayer.android.kt:293)
	at androidx.compose.ui.platform.GraphicsLayerOwnerLayer$$ExternalSyntheticLambda0.invoke(D8$$SyntheticClass:0)
	at androidx.compose.ui.graphics.layer.GraphicsLayer.drawWithChildTracking(AndroidGraphicsLayer.android.kt:480)
	at androidx.compose.ui.graphics.layer.GraphicsLayer.access$drawWithChildTracking(AndroidGraphicsLayer.android.kt:57)
	at androidx.compose.ui.graphics.layer.GraphicsLayer$clipDrawBlock$1.invoke(AndroidGraphicsLayer.android.kt:70)
	at androidx.compose.ui.graphics.layer.GraphicsLayer$clipDrawBlock$1.invoke(AndroidGraphicsLayer.android.kt:65)
	at androidx.compose.ui.graphics.layer.GraphicsLayerV29.record(GraphicsLayerV29.android.kt:323)
	at androidx.compose.ui.graphics.layer.GraphicsLayer.recordInternal(AndroidGraphicsLayer.android.kt:473)
	at androidx.compose.ui.graphics.layer.GraphicsLayer.record-mL-hObY(AndroidGraphicsLayer.android.kt:469)
	at androidx.compose.ui.platform.GraphicsLayerOwnerLayer.updateDisplayList(GraphicsLayerOwnerLayer.android.kt:286)
	at androidx.compose.ui.platform.AndroidComposeView.dispatchDraw(AndroidComposeView.android.kt:2243)
	at android.view.View.draw(View.java:26758)
	at android.view.View.updateDisplayListIfDirty(View.java:25577)
	at android.view.ViewGroup.recreateChildDisplayList(ViewGroup.java:4881)
	at android.view.ViewGroup.dispatchGetDisplayList(ViewGroup.java:4853)
	at android.view.View.updateDisplayListIfDirty(View.java:25521)
	at android.view.ViewGroup.recreateChildDisplayList(ViewGroup.java:4881)
	at android.view.ViewGroup.dispatchGetDisplayList(ViewGroup.java:4853)
	at android.view.View.updateDisplayListIfDirty(View.java:25521)
	at android.view.ViewGroup.recreateChildDisplayList(ViewGroup.java:4881)
	at android.view.ViewGroup.dispatchGetDisplayList(ViewGroup.java:4853)
	at android.view.View.updateDisplayListIfDirty(View.java:25521)
	at android.view.ViewGroup.recreateChildDisplayList(ViewGroup.java:4881)
	at android.view.ViewGroup.dispatchGetDisplayList(ViewGroup.java:4853)
	at android.view.View.updateDisplayListIfDirty(View.java:25521)
	at android.view.ThreadedRenderer.updateViewTreeDisplayList(ThreadedRenderer.java:727)
	at android.view.ThreadedRenderer.updateRootDisplayList(ThreadedRenderer.java:733)
	at android.view.ThreadedRenderer.draw(ThreadedRenderer.java:831)
	at android.view.ViewRootImpl.draw(ViewRootImpl.java:7095)
	at android.view.ViewRootImpl.performDraw(ViewRootImpl.java:6711)
	at android.view.ViewRootImpl.performTraversals(ViewRootImpl.java:5595)
	at android.view.ViewRootImpl.doTraversal(ViewRootImpl.java:3959)
	at android.view.ViewRootImpl$TraversalRunnable.run(ViewRootImpl.java:13034)
	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1961)
	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1970)
	at android.view.Choreographer.doCallbacks(Choreographer.java:1423)
	at android.view.Choreographer.doFrame(Choreographer.java:1348)
	at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:1930)
	at android.os.Handler.handleCallback(Handler.java:1070)
	at android.os.Handler.dispatchMessage(Handler.java:125)
	at android.os.Looper.dispatchMessage(Looper.java:358)
	at android.os.Looper.loopOnce(Looper.java:288)
	at android.os.Looper.loop(Looper.java:392)
	at android.app.ActivityThread.main(ActivityThread.java:10346)
	at java.lang.reflect.Method.invoke(Native Method)
	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:638)
	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:972)
```
