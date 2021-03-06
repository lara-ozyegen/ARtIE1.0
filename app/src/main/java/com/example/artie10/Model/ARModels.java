package com.example.artie10.Model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.example.artie10.InfoPage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.ar.core.HitResult;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
/**
 * @author Lara, Oyku, Sarper, Berk, Enis, Onur, Yaren
 * @date 25/04/2020
 * @version 1.0
 * ARScreen - displays 3D models using google arcore library
 */
public class ARModels implements ValueEventListener {

    //properties
    private Context context;
    private String text;
    private StorageReference modelRef;
    private ModelRenderable renderable;
    private ArFragment fragment;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference modelInfo;
    private String modelInfoText;
    private String s;

    //constructors
    public ARModels(Context context, ArFragment fragment, String s){
        text = s;
        this.context = context;
        this.fragment = fragment;

        FirebaseApp.initializeApp(context);

        FirebaseStorage storage = FirebaseStorage.getInstance();

        //getting the model from database using its name
        modelRef = storage.getReference().child( text + ".glb");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        //getting the model info of the model using its name
        modelInfo = databaseReference.child(text);
        modelInfo.addValueEventListener(this);
    }

    public ARModels( Context context, ArFragment fragment, String s, boolean result){
        this.s = s;
        this.context = context;
        this.fragment = fragment;

    }

    //methods
    public void insertModel(HitResult hitResult){
        AnchorNode anchorNode = new AnchorNode(hitResult.createAnchor());
        anchorNode.setRenderable(renderable);
        fragment.getArSceneView().getScene().addChild(anchorNode);
    }

    public void downloadModel(){
        try {
            File file = File.createTempFile( text , "glb");

            modelRef.getFile(file)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    buildModel(file);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** This is a method to build model to ARscreen
     * @param file
     */
    public void buildModel(File file){

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

    /** This method opens preview but with sending the modelInfoText we got from
     */
    public void openInfoWithText(){
        Intent intent = new Intent( context, InfoPage.class  );
        intent.putExtra("PreviewOfModel", modelInfoText);
        context.startActivity( intent );
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot){ //This is a method to change the text if there is a change in realtime database
        if(dataSnapshot.getValue(String.class) != null){
            modelInfoText = dataSnapshot.getValue(String.class);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError){
    }

}
