package com.android.systemui.wallet.ui;

import android.view.View;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R$id;

/* access modifiers changed from: package-private */
public class WalletCardViewHolder extends RecyclerView.ViewHolder {
    final CardView mCardView;
    WalletCardViewInfo mCardViewInfo;
    final ImageView mImageView;

    WalletCardViewHolder(View view) {
        super(view);
        CardView cardView = (CardView) view.requireViewById(R$id.card);
        this.mCardView = cardView;
        this.mImageView = (ImageView) cardView.requireViewById(R$id.card_image);
    }
}
