import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'food_colors.dart';

abstract class FoodTextStyles {
  static const FontWeight medium = FontWeight.w500;
  static const FontWeight regular = FontWeight.w400;
  static const FontWeight light = FontWeight.w300;

  static const TextStyle h1 = TextStyle(color: FoodColors.onSurfacePrimary, fontSize: 96, fontWeight: medium, height: 1.25);
  static const TextStyle h2 = TextStyle(color: FoodColors.onSurfacePrimary, fontSize: 60, fontWeight: medium, height: 1.25);
  static const TextStyle h3 = TextStyle(color: FoodColors.onSurfacePrimary, fontSize: 48, fontWeight: medium, height: 1.25);
  static const TextStyle h4 = TextStyle(color: FoodColors.onSurfacePrimary, fontSize: 34, fontWeight: medium, height: 1.25);
  static const TextStyle h5 = TextStyle(color: FoodColors.onSurfacePrimary, fontSize: 24, fontWeight: medium, height: 1.25);
  static const TextStyle h6 = TextStyle(color: FoodColors.onSurfacePrimary, fontSize: 20, fontWeight: medium, height: 1.25);
  static const TextStyle subtitle1 = TextStyle(color: FoodColors.onSurfacePrimary, fontSize: 16, fontWeight: regular);
  static const TextStyle subtitle2 = TextStyle(color: FoodColors.onSurfacePrimary, fontSize: 14, fontWeight: regular);
  static const TextStyle body1 = TextStyle(color: FoodColors.onSurfacePrimary, fontSize: 16, fontWeight: regular);
  static const TextStyle body2 = TextStyle(color: FoodColors.onSurfacePrimary, fontSize: 14, fontWeight: regular);
  static const TextStyle button = TextStyle(color: FoodColors.onSurfacePrimary, fontSize: 14, fontWeight: regular);
  static const TextStyle caption = TextStyle(color: FoodColors.onSurfacePrimary, fontSize: 12, fontWeight: light);
}
