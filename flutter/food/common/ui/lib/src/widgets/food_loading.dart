
import 'package:flutter/material.dart';
import '../core/food_colors.dart';

class FoodLoading extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return LinearProgressIndicator(
      backgroundColor: FoodColors.secondary,
      color: FoodColors.primary,
    );
  }
}