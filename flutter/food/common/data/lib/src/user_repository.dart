import 'package:api/api.dart';

class UserRepository {
  UserRepository({required userApi}) : _userApi = userApi;

  final UserApi _userApi;

  Future<UserExistResult> exist(String phoneNumber) async {
    try {
      await _userApi.getUsersUserId(userId: phoneNumber);
      return UserExistResultExist();
    } on Exception catch (e) {
      final apiError = e.toApiError();
      if (apiError?.code == "user_not_exist") {
        return UserExistResultNotExist();
      }
      rethrow;
    }
  }
}

abstract class UserExistResult {}

class UserExistResultExist extends UserExistResult {}

class UserExistResultNotExist extends UserExistResult {}