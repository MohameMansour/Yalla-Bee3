����   3D
 G �
 H � �
  �	 G � �
  �	 G �	 G �	 G �	 G �	 G � �
  �	 G �
 � �
 G �
  �
  �
  � �	  �
 G �
 G �
 G �
 G �	 G � � �
  � J �
 G �	  �	  � J �
 G �
 G �
 G �
 G �	  � � �
 ( �
 G � �
 , � �
 , �
 , �
 , 
 G J J J
 
 G
  J	 J
 J
 G
  Z
  Z � � � Callback InnerClasses UpdateOp POSITION_TYPE_INVISIBLE I ConstantValue     POSITION_TYPE_NEW_OR_LAID_OUT    DEBUG Z TAG Ljava/lang/String; mUpdateOpPool Pool $Landroid/support/v4/util/Pools$Pool; 	Signature XLandroid/support/v4/util/Pools$Pool<Landroid/support/v7/widget/AdapterHelper$UpdateOp;>; mPendingUpdates Ljava/util/ArrayList; ILjava/util/ArrayList<Landroid/support/v7/widget/AdapterHelper$UpdateOp;>; mPostponedList 	mCallback 2Landroid/support/v7/widget/AdapterHelper$Callback; mOnItemProcessedCallback Ljava/lang/Runnable; mDisableRecycler mOpReorderer 'Landroid/support/v7/widget/OpReorderer; mExistingUpdateTypes <init> 5(Landroid/support/v7/widget/AdapterHelper$Callback;)V Code LineNumberTable LocalVariableTable this )Landroid/support/v7/widget/AdapterHelper; callback 6(Landroid/support/v7/widget/AdapterHelper$Callback;Z)V disableRecycler addUpdateOp ^([Landroid/support/v7/widget/AdapterHelper$UpdateOp;)Landroid/support/v7/widget/AdapterHelper; ops 3[Landroid/support/v7/widget/AdapterHelper$UpdateOp; reset ()V 
preProcess op 2Landroid/support/v7/widget/AdapterHelper$UpdateOp; i count StackMapTable � consumePostponedUpdates 	applyMove 5(Landroid/support/v7/widget/AdapterHelper$UpdateOp;)V applyRemove newOp typeChanged vh 
ViewHolder 3Landroid/support/v7/widget/RecyclerView$ViewHolder; position tmpStart tmpCount tmpEnd type applyUpdate payload Ljava/lang/Object; dispatchAndUpdateViewHolders positionMultiplier tmp pos 
updatedPos 
continuous p tmpCnt offsetPositionForPartial %dispatchFirstPassAndUpdateViewHolders 6(Landroid/support/v7/widget/AdapterHelper$UpdateOp;I)V offsetStart updatePositionWithPostponed (II)I start end 	postponed cmd canFindInPreLayout (I)Z applyAdd postponeAndUpdateViewHolders 