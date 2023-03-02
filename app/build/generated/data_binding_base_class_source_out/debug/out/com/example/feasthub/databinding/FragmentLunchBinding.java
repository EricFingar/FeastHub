// Generated by view binder compiler. Do not edit!
package com.example.feasthub.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.feasthub.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentLunchBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final ImageButton lunchBackButton;

  @NonNull
  public final TextView lunchTitle;

  private FragmentLunchBinding(@NonNull FrameLayout rootView, @NonNull ImageButton lunchBackButton,
      @NonNull TextView lunchTitle) {
    this.rootView = rootView;
    this.lunchBackButton = lunchBackButton;
    this.lunchTitle = lunchTitle;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentLunchBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentLunchBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_lunch, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentLunchBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.lunchBackButton;
      ImageButton lunchBackButton = ViewBindings.findChildViewById(rootView, id);
      if (lunchBackButton == null) {
        break missingId;
      }

      id = R.id.lunchTitle;
      TextView lunchTitle = ViewBindings.findChildViewById(rootView, id);
      if (lunchTitle == null) {
        break missingId;
      }

      return new FragmentLunchBinding((FrameLayout) rootView, lunchBackButton, lunchTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}