package com.example.onnote.ui;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final int spanCount;  // Количество колонок в гриде
    private final int spacing;    // Расстояние между элементами
    private final int edgeSpacing; // Боковые отступы от краев экрана
    private final int topSpacing;  // Верхний отступ

    public GridSpacingItemDecoration(int spanCount, int spacing, int edgeSpacing, int topSpacing) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.edgeSpacing = edgeSpacing;
        this.topSpacing = topSpacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // Позиция элемента
        if (position == RecyclerView.NO_POSITION) return;

        int column = position % spanCount; // Определяем колонку

        // Отступ слева: первый элемент в ряду — edgeSpacing, иначе — spacing / 2
        outRect.left = column == 0 ? edgeSpacing : spacing / 2;

        // Отступ справа: последний элемент в ряду — edgeSpacing, иначе — spacing / 2
        outRect.right = column == spanCount - 1 ? edgeSpacing : spacing / 2;
        outRect.top = position < spanCount ? topSpacing : spacing;
        boolean isLastRow = position >= parent.getAdapter().getItemCount() - spanCount;
        outRect.bottom = isLastRow ? 0 : spacing;
    }
}