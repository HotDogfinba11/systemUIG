package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.controls.ControlsServiceInfo;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AppAdapter.kt */
public final class AppAdapter extends RecyclerView.Adapter<Holder> {
    private final AppAdapter$callback$1 callback;
    private final FavoritesRenderer favoritesRenderer;
    private final LayoutInflater layoutInflater;
    private List<ControlsServiceInfo> listOfServices = CollectionsKt__CollectionsKt.emptyList();
    private final Function1<ComponentName, Unit> onAppSelected;
    private final Resources resources;

    /* JADX DEBUG: Multi-variable search result rejected for r7v0, resolved type: kotlin.jvm.functions.Function1<? super android.content.ComponentName, kotlin.Unit> */
    /* JADX WARN: Multi-variable type inference failed */
    public AppAdapter(Executor executor, Executor executor2, Lifecycle lifecycle, ControlsListingController controlsListingController, LayoutInflater layoutInflater2, Function1<? super ComponentName, Unit> function1, FavoritesRenderer favoritesRenderer2, Resources resources2) {
        Intrinsics.checkNotNullParameter(executor, "backgroundExecutor");
        Intrinsics.checkNotNullParameter(executor2, "uiExecutor");
        Intrinsics.checkNotNullParameter(lifecycle, "lifecycle");
        Intrinsics.checkNotNullParameter(controlsListingController, "controlsListingController");
        Intrinsics.checkNotNullParameter(layoutInflater2, "layoutInflater");
        Intrinsics.checkNotNullParameter(function1, "onAppSelected");
        Intrinsics.checkNotNullParameter(favoritesRenderer2, "favoritesRenderer");
        Intrinsics.checkNotNullParameter(resources2, "resources");
        this.layoutInflater = layoutInflater2;
        this.onAppSelected = function1;
        this.favoritesRenderer = favoritesRenderer2;
        this.resources = resources2;
        AppAdapter$callback$1 appAdapter$callback$1 = new AppAdapter$callback$1(executor, this, executor2);
        this.callback = appAdapter$callback$1;
        controlsListingController.observe(lifecycle, appAdapter$callback$1);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Intrinsics.checkNotNullParameter(viewGroup, "parent");
        View inflate = this.layoutInflater.inflate(R$layout.controls_app_item, viewGroup, false);
        Intrinsics.checkNotNullExpressionValue(inflate, "layoutInflater.inflate(R.layout.controls_app_item, parent, false)");
        return new Holder(inflate, this.favoritesRenderer);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.listOfServices.size();
    }

    public void onBindViewHolder(Holder holder, int i) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        holder.bindData(this.listOfServices.get(i));
        holder.itemView.setOnClickListener(new AppAdapter$onBindViewHolder$1(this, i));
    }

    /* compiled from: AppAdapter.kt */
    public static final class Holder extends RecyclerView.ViewHolder {
        private final FavoritesRenderer favRenderer;
        private final TextView favorites;
        private final ImageView icon;
        private final TextView title;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Holder(View view, FavoritesRenderer favoritesRenderer) {
            super(view);
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(favoritesRenderer, "favRenderer");
            this.favRenderer = favoritesRenderer;
            View requireViewById = this.itemView.requireViewById(16908294);
            Intrinsics.checkNotNullExpressionValue(requireViewById, "itemView.requireViewById(com.android.internal.R.id.icon)");
            this.icon = (ImageView) requireViewById;
            View requireViewById2 = this.itemView.requireViewById(16908310);
            Intrinsics.checkNotNullExpressionValue(requireViewById2, "itemView.requireViewById(com.android.internal.R.id.title)");
            this.title = (TextView) requireViewById2;
            View requireViewById3 = this.itemView.requireViewById(R$id.favorites);
            Intrinsics.checkNotNullExpressionValue(requireViewById3, "itemView.requireViewById(R.id.favorites)");
            this.favorites = (TextView) requireViewById3;
        }

        public final void bindData(ControlsServiceInfo controlsServiceInfo) {
            Intrinsics.checkNotNullParameter(controlsServiceInfo, "data");
            this.icon.setImageDrawable(controlsServiceInfo.loadIcon());
            this.title.setText(controlsServiceInfo.loadLabel());
            FavoritesRenderer favoritesRenderer = this.favRenderer;
            ComponentName componentName = controlsServiceInfo.componentName;
            Intrinsics.checkNotNullExpressionValue(componentName, "data.componentName");
            String renderFavoritesForComponent = favoritesRenderer.renderFavoritesForComponent(componentName);
            this.favorites.setText(renderFavoritesForComponent);
            this.favorites.setVisibility(renderFavoritesForComponent == null ? 8 : 0);
        }
    }
}
