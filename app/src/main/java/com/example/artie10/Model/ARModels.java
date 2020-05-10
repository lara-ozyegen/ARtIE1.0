package com.example.artie10.Model;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.ar.core.HitResult;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ARModels {

    //properties
    private Context context;
    private String text;
    private StorageReference modelRef;
    private ModelRenderable renderable;
    private ArFragment fragment;

    //constructors
    public ARModels(Context context, ArFragment fragment, String s){
        text = s;
        this.context = context;
        this.fragment = fragment;
        FirebaseApp.initializeApp(context);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        modelRef = storage.getReference().child( text + ".glb");

    }

    //methods
    public void InsertModel(HitResult hitResult){
        AnchorNode anchorNode = new AnchorNode(hitResult.createAnchor());
        anchorNode.setRenderable(renderable);
        fragment.getArSceneView().getScene().addChild(anchorNode);
    }

    public void DownloadModel(){
        try {
            File file = File.createTempFile( text , "glb");

            modelRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    BuildModel(file);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void BuildModel(File file){
        RenderableSource renderableSource = RenderableSource
                .builder()
                .setSource(context, Uri.parse(file.getPath()), RenderableSource.SourceType.GLB)
                .setRecenterMode( RenderableSource.RecenterMode.ROOT )
                .build();

        ModelRenderable
                .builder()
                .setSource( context, renderableSource )
                .setRegistryId(file.getPath())
                .build()
                .thenAccept(modelRenderable -> {
                    Toast.makeText(context, "Model Built", Toast.LENGTH_SHORT).show();
                    renderable = modelRenderable;
                });
    }
}
