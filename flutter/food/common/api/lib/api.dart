import 'package:dio/dio.dart';
import 'package:food_api_client/food_api_client.dart';

export 'package:food_api_client/food_api_client.dart';

// todo base on environment
final _apiClient = FoodApiClient(basePathOverride: 'http://192.168.2.1:3000/api');

final userApi = _apiClient.getUserApi();

final configApi = _apiClient.getConfigApi();

final sessionApi = _apiClient.getSessionApi();

extension ApiErrorExtensions on Exception {
   ApiError? toApiError() {
      if (this is! DioError) return null;
      final error = this as DioError;
      final errorBody = error.response!.data;
      return _apiClient.serializers.deserializeWith(ApiError.serializer, errorBody);
   }
}