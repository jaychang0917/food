
import 'package:flutter/material.dart';
import 'package:ui/ui.dart';
import '../core/food_colors.dart';
import '../widgets/food_text.dart';

class FoodBanner {
  FoodBanner._({required this.content, required this.type});

  FoodBanner.neutral(String content) : this._(content: content, type: FoodBannerType.neutral);

  FoodBanner.error(String content) : this._(content: content, type: FoodBannerType.error);

  final String content;
  final FoodBannerType type;

  void show(BuildContext context) {
    final snackBar = _build(context);
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }

  static void dismiss(BuildContext context) {
    ScaffoldMessenger.of(context).clearSnackBars();
  }

  SnackBar _build(BuildContext context) {
    return SnackBar(
      padding: const EdgeInsets.symmetric(horizontal: FoodSpacings.small),
      backgroundColor: type == FoodBannerType.neutral ? FoodColors.primary : FoodColors.error,
      content: Row(
        children: [
          Expanded(
            child: FoodText.body2(content).withColor(FoodColors.secondary)
          ),
          GestureDetector(
            onTap: () => dismiss(context),
            child: Icon(
              Icons.clear,
              color: type == FoodBannerType.neutral ? FoodColors.onPrimary : FoodColors.onError,
            ),
          ),
        ],
      ),
      duration: Duration(days: 365),
    );
  }
}

enum FoodBannerType {
  neutral, error
}